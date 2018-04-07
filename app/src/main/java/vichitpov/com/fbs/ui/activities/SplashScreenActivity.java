package vichitpov.com.fbs.ui.activities;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.BaseAppCompatActivity;
import vichitpov.com.fbs.base.InternetConnection;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.UserInformationResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;

public class SplashScreenActivity extends BaseAppCompatActivity {
    private UserInformationManager userInformationManager;
    private ApiService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setDefaultLanguage("en");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        apiService = ServiceGenerator.createService(ApiService.class);
        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));

        if (InternetConnection.isNetworkConnected(this)){
            if (userInformationManager.getUser().getAccessToken().equals("N/A")) {
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }, 3000);
            } else {
                getInformationUser();
            }
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage(getString(R.string.no_internet_connection));
            builder.setPositiveButton("Close", (dialogInterface, i) -> {
                finish();
                moveTaskToBack(true);
                System.exit(0);
            });
        }




    }

    private void getInformationUser() {
        String accessToken = userInformationManager.getUser().getAccessToken();
        if (!userInformationManager.getUser().getAccessToken().equals("N/A")) {
            Call<UserInformationResponse> call = apiService.getUserInformation(accessToken);
            call.enqueue(new Callback<UserInformationResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserInformationResponse> call, @NonNull Response<UserInformationResponse> response) {
                    if (response.isSuccessful()) {
                        userInformationManager.saveInformation(response.body());
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        getInformationUser();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserInformationResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    getInformationUser();
                }
            });
        }
    }


}
