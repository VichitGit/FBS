package vichitpov.com.fbs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vichitpov.com.fbs.R;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linearSold;
    private LinearLayout linearBought;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ImageView imageBack = findViewById(R.id.image_back);
        ImageView imageSetting = findViewById(R.id.image_setting);
        LinearLayout linearEditProfile = findViewById(R.id.linear_edit_profile);



        imageBack.setOnClickListener(this);
        linearEditProfile.setOnClickListener(this);
        imageSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.image_back)
            finish();
        else if (id == R.id.linear_edit_profile)
            startActivity(new Intent(getApplicationContext(), EditUserProfileActivity.class));
        else if (id == R.id.image_setting)
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));


    }
}