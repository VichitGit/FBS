package vichitpov.com.fbs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vichitpov.com.fbs.R;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView textToolbar;
    private LinearLayout linearEditProfile, linearSold, linearBought;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ImageView imageBack = findViewById(R.id.image_back);
        linearEditProfile = findViewById(R.id.linear_edit_profile);


//        viewPager = findViewById(R.id.view_pager);
//        tabLayout = findViewById(R.id.tab_layout);
        textToolbar = findViewById(R.id.text_toolbar);


//        setUpTabLayout();

        imageBack.setOnClickListener(this);
        linearEditProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.image_back)
            finish();
        else if (id == R.id.linear_edit_profile)
            startActivity(new Intent(getApplicationContext(), EditUserProfileActivity.class));

    }

//    private void setUpTabLayout() {
//        UserProfileBoughtFragment profileBoughtFragment = new UserProfileBoughtFragment();
//        UserProfileSoldFragment profileSoldFragment = new UserProfileSoldFragment();
//        UserProfileFragment profileFragment = new UserProfileFragment();
//
//        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), this);
//        adapter.addTab(new TabModel(getString(R.string.tab_profile), profileFragment));
//        adapter.addTab(new TabModel(getString(R.string.tab_bought), profileBoughtFragment));
//        adapter.addTab(new TabModel(getString(R.string.tab_sold), profileSoldFragment));
//        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(2);
//        tabLayout.setupWithViewPager(viewPager);
//
//        tabLayout.addOnTabSelectedListener(this);
//
//    }
//
//    @Override
//    public void onTabSelected(TabLayout.Tab tab) {
//        int position = tab.getPosition();
//        if (position == 0) {
//            textToolbar.setText(getString(R.string.tab_profile));
//        } else if (position == 1) {
//            textToolbar.setText(getString(R.string.tab_sold));
//        } else if (position == 2) {
//            textToolbar.setText(getString(R.string.tab_bought));
//        }
//
//    }
//
//    @Override
//    public void onTabUnselected(TabLayout.Tab tab) {
//
//    }
//
//    @Override
//    public void onTabReselected(TabLayout.Tab tab) {
//
//    }


}