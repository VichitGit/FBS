package vichitpov.com.fbs.model;

import android.support.v4.app.Fragment;

/**
 * Created by Vichit Pov on 12/16/2017.
 */

public class TabModel {
    private String title;
    private Fragment fragment;

    public TabModel(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

}
