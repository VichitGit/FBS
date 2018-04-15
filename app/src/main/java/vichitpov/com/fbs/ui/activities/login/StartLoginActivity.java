package vichitpov.com.fbs.ui.activities.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.BaseAppCompatActivity;
import vichitpov.com.fbs.constant.AnyConstant;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.UserInformationResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activities.MainActivity;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.SEND_SMS;

public class StartLoginActivity extends BaseAppCompatActivity {
    public static int APP_REQUEST_CODE = 99;
    private TextView textLoginPhone, textLoginEmail;
    private String accessToken;
    private ProgressDialog progressDialog;
    private UserInformationManager userInformationManager;
    public static final int RequestPermissionCode = 101;
    private BannerSlider bannerSlider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.progress_loading_title));
        progressDialog.setMessage(getString(R.string.progress_loading_message));
        progressDialog.setCancelable(false);

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        if (!userInformationManager.getUser().getAccessToken().equals("N/A")) {
            AccessToken accessToken = AccountKit.getCurrentAccessToken();
            if (accessToken != null) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }

        initView();
        setUpSlider();
        onClickListener();

    }

    private void setUpSlider() {
        List<Banner> imageSliderList = new ArrayList<>();
        imageSliderList.add(new DrawableBanner(R.drawable.ic_welcome1));
        imageSliderList.add(new DrawableBanner(R.drawable.ic_welcome2));
        imageSliderList.add(new DrawableBanner(R.drawable.ic_welcome3));
        bannerSlider.setBanners(imageSliderList);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) {
            // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);

            //when verify or error with account kit this condition will invoke
            if (loginResult.getError() != null) {
                startActivity(getIntent());
            } else {
                /*
                  After verify success it's will process onActivityForResult, so if all above is true, this
                  condition is available and account kit provide us to get access token. finally, after we
                  got access token, we must to send to those access token to server and than server will check
                  if access token that sent is new user, we need to provide register screen to user but if access token
                  that sent is old user, we no need to provide register screen to user.
                */
                if (loginResult.getAccessToken() != null) {
                    progressDialog.show();

                    accessToken = loginResult.getAccessToken().getToken();
                    ApiService apiService = ServiceGenerator.createService(ApiService.class);

                    Call<UserInformationResponse> call = apiService.getUserInformation(accessToken);
                    call.enqueue(new Callback<UserInformationResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<UserInformationResponse> call, @NonNull Response<UserInformationResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getData().getStatus().contains("new")) {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(getApplicationContext(), RegisterUserActivity.class);
                                    intent.putExtra(AnyConstant.ACCESS_TOKEN, accessToken);
                                    intent.putExtra(AnyConstant.PHONE, response.body().getData().getPhone());
                                    startActivity(intent);
                                    finish();

                                } else if (response.body().getData().getStatus().contains("old")) {
                                    OneSignal.sendTag(AnyConstant.USER_ID, response.body().getData().getId() + "");

                                    userInformationManager.deleteAccessToken();
                                    userInformationManager.saveAccessToken(accessToken);
                                    userInformationManager.saveInformation(response.body());
                                    progressDialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();

                                }

                            } else if (response.code() == 401) {
                                progressDialog.dismiss();
                                startActivity(getIntent());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<UserInformationResponse> call, @NonNull Throwable t) {
                            progressDialog.dismiss();
                            t.printStackTrace();
                        }
                    });
                }
            }
        }
    }

    public void phoneLogin(LoginType loginType) {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder = new AccountKitConfiguration.AccountKitConfigurationBuilder(loginType, AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    private void onClickListener() {
        progressDialog.show();
        new Handler().postDelayed(() -> progressDialog.dismiss(), 1500);

        textLoginPhone.setOnClickListener(view -> {
            if (checkingPermissionIsEnabledOrNot()) {
                phoneLogin(LoginType.PHONE);
            } else {
                requestMultiplePermission();
            }
        });

        textLoginEmail.setOnClickListener(view -> {
            if (checkingPermissionIsEnabledOrNot()) {
                phoneLogin(LoginType.EMAIL);
            } else {
                requestMultiplePermission();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean isCameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean isAccessFineLocationPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean isAccessCrossLocationPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean isReadExternalStoragePermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean isReadPhoneStatePermission = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean isSendSmsPermission = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean isReceiveSmsPermission = grantResults[6] == PackageManager.PERMISSION_GRANTED;

                    if (isCameraPermission && isAccessFineLocationPermission && isAccessCrossLocationPermission && isReadExternalStoragePermission && isReadPhoneStatePermission && isSendSmsPermission && isReceiveSmsPermission) {

                        Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Request permission app!");
                        builder.setMessage("You must to allow all permission to use feature!");
                        builder.setPositiveButton("GO TO SETTING", (dialogInterface, i) -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            dialogInterface.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }

                break;
        }
    }

    private void requestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(StartLoginActivity.this, new String[]{
                CAMERA, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, READ_EXTERNAL_STORAGE,
                READ_PHONE_STATE, SEND_SMS, RECEIVE_SMS
        }, RequestPermissionCode);

    }

    public boolean checkingPermissionIsEnabledOrNot() {

        int firstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int secondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int thirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int forthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int fivePermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int sixPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), RECEIVE_SMS);
        int sevenPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return firstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                secondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                thirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                forthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                fivePermissionResult == PackageManager.PERMISSION_GRANTED &&
                sixPermissionResult == PackageManager.PERMISSION_GRANTED &&
                sevenPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void initView() {
        textLoginPhone = findViewById(R.id.textLoginPhone);
        textLoginEmail = findViewById(R.id.textLoginEmail);
        bannerSlider = findViewById(R.id.banner_slider);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
