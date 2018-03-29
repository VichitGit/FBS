package vichitpov.com.fbs.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.BaseAppCompatActivity;

public class SplashScreenActivity extends BaseAppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setDefaultLanguage("en");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }, 3000);

    }
}
