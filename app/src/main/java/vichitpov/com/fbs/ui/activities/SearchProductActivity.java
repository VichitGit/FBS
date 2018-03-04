package vichitpov.com.fbs.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import vichitpov.com.fbs.R;

public class SearchProductActivity extends AppCompatActivity implements View.OnClickListener {
    private android.support.v7.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        ImageView imageBack = findViewById(R.id.image_back);
        searchView = findViewById(R.id.search_product);

        imageBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
