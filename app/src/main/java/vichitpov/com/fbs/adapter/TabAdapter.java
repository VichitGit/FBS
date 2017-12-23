package vichitpov.com.fbs.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.model.TabModel;

/**
 * Created by Goldenware on 12/16/2017.
 */

public class TabAdapter extends FragmentPagerAdapter {
    List<TabModel> tabList;
    Context context;

    public TabAdapter(FragmentManager fm, Context context) {
        super(fm);
        tabList = new ArrayList<>();
        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {
        return tabList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return tabList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position).getTitle();
    }

    public void addTab(TabModel tab) {
        tabList.add(tab);

    }
}
