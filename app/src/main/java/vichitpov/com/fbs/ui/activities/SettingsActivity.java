package vichitpov.com.fbs.ui.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.BaseAppCompatActivity;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.ui.activities.login.StartLoginActivity;

public class SettingsActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private UserInformationManager userInformationManager;
    private String accessToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        accessToken = userInformationManager.getUser().getAccessToken();

        ImageView imageBack = findViewById(R.id.image_back);
        LinearLayout linearLanguage = findViewById(R.id.linear_change_language);
        LinearLayout linearLogout = findViewById(R.id.linear_logout);

        if (accessToken.equals("N/A")) {
            linearLogout.setVisibility(View.GONE);
        } else {
            linearLogout.setVisibility(View.VISIBLE);
        }

        imageBack.setOnClickListener(this);
        linearLanguage.setOnClickListener(this);

        linearLogout.setOnClickListener(view -> {
            if (accessToken.equals("N/A")) {
                startActivity(new Intent(this, StartLoginActivity.class));
            } else {
                logoutAccount();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.linear_change_language) {
            changeLanguage();
        } else if (id == R.id.image_back) {
            finish();
        }
    }


    private void logoutAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout account?");
        builder.setMessage("Do you want to logout your account?");
        builder.setCancelable(true);

        builder.setPositiveButton("Logout", (dialogInterface, i) -> {
            userInformationManager.deleteUserInformation();
            userInformationManager.deleteAccessToken();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    //change language
    private void changeLanguage() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog_language);
        dialog.show();
        LinearLayout english = dialog.findViewById(R.id.linearEnglish);
        LinearLayout khmer = dialog.findViewById(R.id.linearKhmer);

        english.setOnClickListener(view -> {
            setLanguage("en");
            dialog.dismiss();
        });

        khmer.setOnClickListener(view -> {
            setLanguage("km");
            dialog.dismiss();

        });
    }
}
