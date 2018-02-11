package vichitpov.com.fbs.ui.activity.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.UserBoughtAdapter;
import vichitpov.com.fbs.model.UserModel;

public class RePostToBuyActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_post_to_buy);

        ImageView imageBack = findViewById(R.id.image_back);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<UserModel> postList = new ArrayList<>();
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank.", "Phnom Penh", 500, "", "Unavailable"));


        UserBoughtAdapter adapter = new UserBoughtAdapter(this, postList);
        recyclerView.setAdapter(adapter);

        imageBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
