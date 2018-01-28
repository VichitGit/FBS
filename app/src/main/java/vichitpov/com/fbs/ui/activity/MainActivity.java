package vichitpov.com.fbs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View navHeaderView = navigationView.getHeaderView(0);
        FabSpeedDial fab = findViewById(R.id.fab_speed_dial);
        Toolbar toolbar = findViewById(R.id.toolbar);

        drawer = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.view_pager_recent);
        tabLayout = findViewById(R.id.tab_layout_recent);

        TextView textLogin = navHeaderView.findViewById(R.id.text_login);
        TextView textRegister = navHeaderView.findViewById(R.id.text_register);


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            startActivity(new Intent(getApplicationContext(), SearchProductActivity.class));
        }
        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

            case R.id.nav_logout:
                logoutUser();
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

    private void logoutUser() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();

        TextView textTitle = dialogView.findViewById(R.id.text_title);
        TextView textMessage = dialogView.findViewById(R.id.text_message);
        TextView textLogout = dialogView.findViewById(R.id.text_logout);
        TextView textCancel = dialogView.findViewById(R.id.text_cancel);

        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        textLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
















