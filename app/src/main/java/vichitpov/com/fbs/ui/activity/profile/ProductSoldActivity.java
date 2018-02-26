package vichitpov.com.fbs.ui.activity.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter1.UserSoldAdapter;
import vichitpov.com.fbs.model.UserModel;

public class ProductSoldActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private List<UserModel> postList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_sold);

        ImageView imageBack = findViewById(R.id.image_back);

        recyclerView = findViewById(R.id.recycler_sold);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        setFixData();

        UserSoldAdapter adapter = new UserSoldAdapter(getApplicationContext(), postList);
        recyclerView.setAdapter(adapter);

        imageBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        finish();
    }

    private void setFixData() {
        postList = new ArrayList<>();
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
        postList.add(new UserModel("I want the sell sumsung s7 plush with power blank.", "Phnom Penh", 300, "", "Available"));
    }


}
