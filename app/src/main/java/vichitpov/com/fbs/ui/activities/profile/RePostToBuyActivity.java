package vichitpov.com.fbs.ui.activities.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import vichitpov.com.fbs.R;

public class RePostToBuyActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_post_to_buy);

        ImageView imageBack = findViewById(R.id.image_back);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
