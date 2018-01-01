package vichitpov.com.fbs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.TabAdapter;
import vichitpov.com.fbs.model.TabModel;
import vichitpov.com.fbs.ui.fragments.BuyerRecentItemFragment;
import vichitpov.com.fbs.ui.fragments.SellerRecentItemFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private DrawerLayout drawer;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.view_pager_recent);
        tabLayout = findViewById(R.id.tab_layout_recent);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setUpTabLayout();

        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(this);
    }

    private void setUpTabLayout() {
        SellerRecentItemFragment sellerRecentItemFragment = new SellerRecentItemFragment();
        BuyerRecentItemFragment buyerRecentItemFragment = new BuyerRecentItemFragment();
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), this);
        adapter.addTab(new TabModel("New Seller", sellerRecentItemFragment));
        adapter.addTab(new TabModel("New Buyer", buyerRecentItemFragment));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);//set it for handle loading data again and again
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), ProductActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                break;
            case R.id.nav_category:
                startActivity(new Intent(this, MainCategoryActivity.class));
                break;
            case R.id.nav_account:
                startActivity(new Intent(this, UserProfileActivity.class));
                break;
            case R.id.nav_favorite:
                break;
            case R.id.nav_change_language:
                break;
            case R.id.nav_about_us:
                break;
            case R.id.nav_settings:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

















