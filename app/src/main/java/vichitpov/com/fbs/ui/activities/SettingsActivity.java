package vichitpov.com.fbs.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.preference.UserInformationManager;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private UserInformationManager userInformationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView imageLogout = findViewById(R.id.image_logout);
        ImageView imageBack = findViewById(R.id.image_back);

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));

        imageLogout.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.image_logout) {
            if (!userInformationManager.getUser().getAccessToken().equals("N/A")) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            }

        } else if (id == R.id.image_back) {
            finish();
        }
    }
}
