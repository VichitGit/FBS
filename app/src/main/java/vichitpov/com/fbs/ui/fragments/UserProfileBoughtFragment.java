package vichitpov.com.fbs.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vichitpov.com.fbs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileBoughtFragment extends Fragment {


    public UserProfileBoughtFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile_bought, container, false);
    }

    /**
     * A simple {@link Fragment} subclass.
     */
    public static class SellerRecentItemFragment extends Fragment {


        public SellerRecentItemFragment() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_seller_recent_item, container, false);
        }

    }
}
