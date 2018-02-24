package vichitpov.com.fbs.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.ui.activity.PostToBuyActivity;
import vichitpov.com.fbs.ui.activity.PostToSellActivity;

public class StartLoginActivity extends AppCompatActivity {
    public static int APP_REQUEST_CODE = 99;
    private TextView textLogin, textSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_login);

        initView();
        onClickListener();

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

        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                //showErrorActivity(loginResult.getError());
                Log.e("pppp loginResultError", loginResult.getError().toString());

            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
                Log.e("pppp wasCancelled", "Login Cancelled");
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                    Log.e("pppp Success", loginResult.getAccessToken().getAccountId());
                    Log.e("pppp getAccessToken", loginResult.getAccessToken().getToken());
                } else {
                    toastMessage = String.format("Success:%s...", loginResult.getAuthorizationCode().substring(0, 10));
                    Log.e("pppp else", loginResult.getAuthorizationCode().substring(0, 10));
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...
                startActivity(new Intent(getApplicationContext(), RegisterUserActivity.class));
            }

            // Surface the result to your user in an appropriate way.
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
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
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneLogin(view);
            }
        });
    }

    private void initView() {
        textLogin = findViewById(R.id.textLogin);
        textSignUp = findViewById(R.id.textSignUp);

    }


}
