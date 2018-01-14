package vichitpov.com.fbs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import vichitpov.com.fbs.R;

public class StartLoginActivity extends AppCompatActivity {
    private TextView textLoginDifferent, textRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_login);

        textLoginDifferent = findViewById(R.id.text_login_different);
        textRegister = findViewById(R.id.text_register);

        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterUserActivity.class));

            }
        });

        textLoginDifferent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginDifferentActivity.class));
            }
        });


    }
}
