package vichitpov.com.fbs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.TabAdapter;
import vichitpov.com.fbs.model.TabModel;
import vichitpov.com.fbs.ui.fragments.BuyerRecentItemFragment;
import vichitpov.com.fbs.ui.fragments.SellerRecentItemFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private DrawerLayout drawer;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView textLogin, textRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View navHeaderView = navigationView.getHeaderView(0);
        final FabSpeedDial fab = findViewById(R.id.fab_speed_dial);
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.view_pager_recent);
        tabLayout = findViewById(R.id.tab_layout_recent);
        textLogin = navHeaderView.findViewById(R.id.text_login);
        textRegister = navHeaderView.findViewById(R.id.text_register);


        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setUpTabLayout();

        navigationView.setNavigationItemSelectedListener(this);
        textLogin.setOnClickListener(this);
        textRegister.setOnClickListener(this);
        fab.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_post_buy) {

                    startActivity(new Intent(getApplicationContext(), PostToSellActivity.class));

                } else {

                    startActivity(new Intent(getApplicationContext(), PostToBuyActivity.class));
                }

                return false;
            }
        });
    }


    private void setUpTabLayout() {
        SellerRecentItemFragment sellerRecentItemFragment = new SellerRecentItemFragment();
        BuyerRecentItemFragment buyerRecentItemFragment = new BuyerRecentItemFragment();
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), this);
        adapter.addTab(new TabModel(getString(R.string.tab_new_seller), sellerRecentItemFragment));
        adapter.addTab(new TabModel(getString(R.string.tab_new_buyer), buyerRecentItemFragment));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);//set it for handle loading data again and again
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_login:
                startActivity(new Intent(getApplicationContext(), StartLoginActivity.class));
                break;
            case R.id.text_register:
                startActivity(new Intent(getApplicationContext(), RegisterUserActivity.class));
                break;
        }
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
            case R.id.nav_save_category:
                startActivity(new Intent(this, BookmarkCategoriesActivity.class));
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

















