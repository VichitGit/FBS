package vichitpov.com.fbs.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.UserBoughtAdapter;
import vichitpov.com.fbs.model.UserModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileBoughtFragment extends Fragment {
    private RecyclerView recyclerView;


    public UserProfileBoughtFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile_bought, container, false);
        recyclerView = view.findViewById(R.id.recycler_user_bought);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<UserModel> postList = new ArrayList<>();
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300));


        UserBoughtAdapter adapter = new UserBoughtAdapter(getActivity(),postList);
        recyclerView.setAdapter(adapter);

    }
}
