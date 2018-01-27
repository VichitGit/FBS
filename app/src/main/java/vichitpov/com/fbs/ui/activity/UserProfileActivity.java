package vichitpov.com.fbs.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.TabAdapter;
import vichitpov.com.fbs.model.TabModel;
import vichitpov.com.fbs.ui.fragments.UserProfileBoughtFragment;
import vichitpov.com.fbs.ui.fragments.UserProfileFragment;
import vichitpov.com.fbs.ui.fragments.UserProfileSoldFragment;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView textToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ImageView imageBack = findViewById(R.id.image_back);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        textToolbar = findViewById(R.id.text_toolbar);
        
        setUpTabLayout();

        imageBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    private void setUpTabLayout() {
        UserProfileBoughtFragment profileBoughtFragment = new UserProfileBoughtFragment();
        UserProfileSoldFragment profileSoldFragment = new UserProfileSoldFragment();
        UserProfileFragment profileFragment = new UserProfileFragment();

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), this);
        adapter.addTab(new TabModel(getString(R.string.tab_profile), profileFragment));
        adapter.addTab(new TabModel(getString(R.string.tab_bought), profileBoughtFragment));
        adapter.addTab(new TabModel(getString(R.string.tab_sold), profileSoldFragment));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        if (position == 0) {
            textToolbar.setText(getString(R.string.tab_profile));
        } else if (position == 1) {
            textToolbar.setText(getString(R.string.tab_sold));
        } else if (position == 2) {
            textToolbar.setText(getString(R.string.tab_bought));
        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}