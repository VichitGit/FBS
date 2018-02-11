package vichitpov.com.fbs.ui.activity.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.UserBoughtAdapter;
import vichitpov.com.fbs.model.UserModel;

public class ProductBoughtActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_bought);

        ImageView imageBack = findViewById(R.id.image_back);


        imageBack.setOnClickListener(this);


        RecyclerView recyclerView = findViewById(R.id.recycler_bought);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<UserModel> postList = new ArrayList<>();
        postList.add(new UserModel("I want the buy iphone7 plush with power blank", "Phnom Penh", 500, "", "Available"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank", "Phnom Penh", 500, "", "Available"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank", "Phnom Penh", 500, "", "Available"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank", "Phnom Penh", 500, "", "Available"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank", "Phnom Penh", 500, "", "Available"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank", "Phnom Penh", 500, "", "Available"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank", "Phnom Penh", 500, "", "Available"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank", "Phnom Penh", 500, "", "Available"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank", "Phnom Penh", 500, "", "Available"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank", "Phnom Penh", 500, "", "Available"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank", "Phnom Penh", 500, "", "Available"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank", "Phnom Penh", 500, "", "Available"));
        postList.add(new UserModel("I want the buy iphone7 plush with power blank", "Phnom Penh", 500, "", "Available"));


        UserBoughtAdapter adapter = new UserBoughtAdapter(this, postList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
