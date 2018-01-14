package vichitpov.com.fbs.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.TabAdapter;
import vichitpov.com.fbs.model.TabModel;
import vichitpov.com.fbs.ui.fragments.BuyProductFragment;
import vichitpov.com.fbs.ui.fragments.SellProductFragment;

public class ProductActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        setUpTabLayout();
    }

    private void setUpTabLayout() {
        SellProductFragment sellProductFragment = new SellProductFragment();
        BuyProductFragment buyProductFragment = new BuyProductFragment();

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), this);
        adapter.addTab(new TabModel(getString(R.string.tab_sell_product), sellProductFragment));
        adapter.addTab(new TabModel(getString(R.string.tab_buy_product), buyProductFragment));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);//set it for handle loading data again and again
        tabLayout.setupWithViewPager(viewPager);


    }
}
