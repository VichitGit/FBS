package vichitpov.com.fbs.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.TabAdapter;
import vichitpov.com.fbs.model.TabModel;
import vichitpov.com.fbs.ui.fragments.UserProfileBoughtFragment;
import vichitpov.com.fbs.ui.fragments.UserProfileFragment;
import vichitpov.com.fbs.ui.fragments.UserProfileSoldFragment;

public class UserProfileActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        setUpTabLayout();
    }

    private void setUpTabLayout() {
        UserProfileBoughtFragment profileBoughtFragment = new UserProfileBoughtFragment();
        UserProfileSoldFragment profileSoldFragment = new UserProfileSoldFragment();
        UserProfileFragment profileFragment = new UserProfileFragment();

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), this);
        adapter.addTab(new TabModel("Profile", profileFragment));
        adapter.addTab(new TabModel("Bought", profileBoughtFragment));
        adapter.addTab(new TabModel("Sold", profileSoldFragment));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
    }
}