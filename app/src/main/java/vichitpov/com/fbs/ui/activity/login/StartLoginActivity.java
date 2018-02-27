package vichitpov.com.fbs.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.preferece.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.UserInformationResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activity.MainActivity;
import vichitpov.com.fbs.ui.activity.PostToBuyActivity;

public class StartLoginActivity extends AppCompatActivity {
    public static int APP_REQUEST_CODE = 99;
    private TextView textLogin, textSignUp;
    private ApiService apiService;
    private String accessToken;
    private UserInformationManager userInformationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_login);

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));

        initView();
        onClickListener();
        setUpSlider();


    }

    private void setUpSlider() {
        BannerSlider bannerSlider = findViewById(R.id.banner_slider);
        List<Banner> banners = new ArrayList<>();
        banners.add(new DrawableBanner(R.drawable.image_silde));
        banners.add(new DrawableBanner(R.drawable.image_silde));
        banners.add(new DrawableBanner(R.drawable.image_silde));
        bannerSlider.setBanners(banners);

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
                    accessToken = loginResult.getAccessToken().getToken();
                    apiService = ServiceGenerator.createService(ApiService.class);
                    Call<UserInformationResponse> call = apiService.getUserInformation(accessToken);
                    call.enqueue(new Callback<UserInformationResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<UserInformationResponse> call, @NonNull Response<UserInformationResponse> response) {
                            if (response.isSuccessful()) {

                                if (response.body().getData().getStatus().contains("new")) {

                                    //save user information into share preference
                                    userInformationManager.saveInformation(response.body());
                                    userInformationManager.saveAccessToken(accessToken);
                                    startActivity(new Intent(getApplicationContext(), RegisterUserActivity.class));

                                } else if (response.body().getData().getStatus().contains("old")) {

                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                }
                            } else if (response.code() == 401) {
                                startActivity(getIntent());
                            }
                        }

                        @Override
                        public void onFailure(Call<UserInformationResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        }
    }


    public void phoneLogin(View view) {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE, AccountKitActivity.ResponseType.TOKEN);

        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @SuppressLint("ResourceAsColor")
    private void dialogBottom() {
        BottomSheetMenuDialog dialog = new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setIconTintColorResource(R.color.colorPrimary)
                .setItemTextColor(R.color.colorPrimary)
                .setMenu(R.menu.login_with)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.dialog_bottom_login_phone) {
                            startActivity(new Intent(getApplicationContext(), PostToBuyActivity.class));
                        } else if (item.getItemId() == R.id.dialog_bottom_login_email) {
                        }

                    }
                })
                .createDialog();

        dialog.show();
    }

    private void onClickListener() {
        textLogin.setOnClickListener(this::phoneLogin);
    }

    private void initView() {
        textLogin = findViewById(R.id.textLoginPhone);
        textSignUp = findViewById(R.id.textLoginEmail);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
