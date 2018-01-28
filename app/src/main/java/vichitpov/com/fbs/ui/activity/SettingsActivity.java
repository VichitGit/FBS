package vichitpov.com.fbs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import vichitpov.com.fbs.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        LinearLayout linearBookmarkCategories = findViewById(R.id.linear_bookmark_categories);
        ImageView imageLogout = findViewById(R.id.image_logout);
        ImageView imageBack = findViewById(R.id.image_back);


        linearBookmarkCategories.setOnClickListener(this);
        imageLogout.setOnClickListener(this);
        imageBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.linear_bookmark_categories)
            startActivity(new Intent(this, BookmarkCategoriesActivity.class));
        else if (id == R.id.image_logout)
            Toast.makeText(this, "Logout an account...!", Toast.LENGTH_SHORT).show();
        else if (id == R.id.image_back)
            finish();
    }
}
