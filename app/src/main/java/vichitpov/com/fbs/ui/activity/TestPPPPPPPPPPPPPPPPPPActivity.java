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

public class TestPPPPPPPPPPPPPPPPPPActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pppppppppppppppppp);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        setUpTabLayout();
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

    }
}
