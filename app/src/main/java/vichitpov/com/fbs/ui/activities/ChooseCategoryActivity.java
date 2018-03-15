package vichitpov.com.fbs.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.IntentData;
import vichitpov.com.fbs.constant.RequestCode;
import vichitpov.com.fbs.retrofit.response.CategoriesResponse;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;

public class ChooseCategoryActivity extends AppCompatActivity {
    private ExpandingList mExpandingList;
    private List<CategoriesResponse.Data> categoriesResponses;
    private HashMap<String, Integer> mapCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        ImageView imageBack = findViewById(R.id.imageBack);
        mExpandingList = findViewById(R.id.expanding_list_main);

        checkIntentMainActivity();
        checkIntentPostToBuy();
        getAllCategories();

        imageBack.setOnClickListener(view -> finish());


    }

    private boolean checkIntentMainActivity() {
        String intentMainActivity = getIntent().getStringExtra(IntentData.SEND_FROM_MAIN_ACTIVITY);
        if (intentMainActivity != null) {
            if (intentMainActivity.equals(IntentData.SEND_FROM_MAIN_ACTIVITY)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean checkIntentPostToBuy() {
        String intentPostToBuy = getIntent().getStringExtra(IntentData.POST_TO_BUY);
        if (intentPostToBuy != null) {
            if (intentPostToBuy.equals(IntentData.POST_TO_BUY)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void getAllCategories() {
        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        Call<CategoriesResponse> call = apiService.getAllCategories();
        call.enqueue(new Callback<CategoriesResponse>() {
            @SuppressLint("UseSparseArrays")
            @Override
            public void onResponse(@NonNull Call<CategoriesResponse> call, @NonNull Response<CategoriesResponse> response) {
                if (response.isSuccessful()) {
                    categoriesResponses = response.body().getData();
                    mapCategory = new HashMap<>();


                    for (int i = 0; i < categoriesResponses.size(); i++) {
                        List<String> subItems = new ArrayList<>();
                        Random rnd = new Random();
                        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                        for (int c = 0; c < categoriesResponses.get(i).getCategorychildren().size(); c++) {
                            int idCategory = categoriesResponses.get(i).getCategorychildren().get(c).getId();
                            String categoryName = categoriesResponses.get(i).getCategorychildren().get(c).getCategoryName();
                            subItems.add(categoriesResponses.get(i).getCategorychildren().get(c).getCategoryName());

                            mapCategory.put(categoryName, idCategory);
                        }

                        addItem(categoriesResponses.get(i).getCategoryname(), subItems, color);
                    }

                } else {
                    Log.e("pppp", response.code() + " = " + response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<CategoriesResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("pppp", t.getMessage());
            }
        });


    }


    private void addItem(String title, List<String> subItem, int color) {
        ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout_category);
        if (item != null) {
            item.setIndicatorColor(color);
            ((TextView) item.findViewById(R.id.title)).setText(title);
            item.createSubItems(subItem.size());
            for (int i = 0; i < item.getSubItemsCount(); i++) {
                //Let's get the created sub item by its index
                final View view = item.getSubItemView(i);

                //Let's set some values in
                configureSubItem(item, view, subItem.get(i));
            }
        }


    }

    private void configureSubItem(final ExpandingItem item, final View view, String subTitle) {

        ((TextView) view.findViewById(R.id.sub_title)).setText(subTitle);
        view.findViewById(R.id.sub_title).setOnClickListener(v -> {

            for (String categoryName : mapCategory.keySet()) {
                if (subTitle.equals(categoryName)) {

                    String categoryId = String.valueOf(mapCategory.get(categoryName));
                    if (checkIntentMainActivity()) {
                        Intent intent = new Intent(getApplicationContext(), ProductSellerCategoryActivity.class);
                        intent.putExtra("CATEGORY_ID", categoryId);
                        intent.putExtra("CATEGORY_NAME", categoryName);
                        startActivity(intent);
                    } else if (checkIntentPostToBuy()) {
                        Log.e("pppp", "checkIntentPostToBuy");
                        Intent data = new Intent();
                        data.putExtra(RequestCode.CATEGORY_ID, categoryId);
                        data.putExtra(RequestCode.CATEGORY_NAME, subTitle);
                        setResult(RequestCode.POST_TO_BUY, data);
                        finish();

                    } else {
                        Log.e("pppp", "else");
                        Intent data = new Intent();
                        data.putExtra(RequestCode.CATEGORY_ID, categoryId);
                        data.putExtra(RequestCode.CATEGORY_NAME, subTitle);
                        setResult(RequestCode.CHOOSE_CATEGORY, data);
                        finish();
                    }
                }
            }
        });
    }
}







