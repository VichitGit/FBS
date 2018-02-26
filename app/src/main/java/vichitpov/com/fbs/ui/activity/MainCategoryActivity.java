package vichitpov.com.fbs.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter1.CategoryAdapter;
import vichitpov.com.fbs.model.CategoriesModel;

public class MainCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);

        RecyclerView recyclerView = findViewById(R.id.recycler_main_category);
        ImageView imageBack = findViewById(R.id.image_back);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<CategoriesModel> categoriesList = new ArrayList<>();
        categoriesList.add(new CategoriesModel("Car", "500"));
        categoriesList.add(new CategoriesModel("Shirt", "123"));
        categoriesList.add(new CategoriesModel("Television", "400"));
        categoriesList.add(new CategoriesModel("Phone", "123"));
        categoriesList.add(new CategoriesModel("Computer", "123"));
        categoriesList.add(new CategoriesModel("Sport", "123"));
        categoriesList.add(new CategoriesModel("Shirt", "123"));
        categoriesList.add(new CategoriesModel("Shirt", "123"));
        categoriesList.add(new CategoriesModel("Shirt", "123"));
        categoriesList.add(new CategoriesModel("Television", "400"));
        categoriesList.add(new CategoriesModel("Phone", "123"));
        categoriesList.add(new CategoriesModel("Computer", "123"));
        categoriesList.add(new CategoriesModel("Sport", "123"));
        categoriesList.add(new CategoriesModel("Shirt", "123"));
        categoriesList.add(new CategoriesModel("Shirt", "123"));
        categoriesList.add(new CategoriesModel("Sport", "123"));
        categoriesList.add(new CategoriesModel("Shirt", "123"));
        categoriesList.add(new CategoriesModel("Shirt", "123"));

        categoriesList.add(new CategoriesModel("Sport", "123"));
        categoriesList.add(new CategoriesModel("Shirt", "123"));
        categoriesList.add(new CategoriesModel("Shirt", "123"));

        categoriesList.add(new CategoriesModel("Sport", "123"));
        categoriesList.add(new CategoriesModel("Shirt", "123"));
        categoriesList.add(new CategoriesModel("Shirt", "123"));
        categoriesList.add(new CategoriesModel("Sport", "123"));
        categoriesList.add(new CategoriesModel("Shirt", "123"));
        categoriesList.add(new CategoriesModel("Shirt", "123"));



        CategoryAdapter adapter = new CategoryAdapter(categoriesList, getApplicationContext());
        recyclerView.setAdapter(adapter);

        imageBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
