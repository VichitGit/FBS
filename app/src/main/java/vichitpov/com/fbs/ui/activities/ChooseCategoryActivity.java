package vichitpov.com.fbs.ui.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.retrofit.response.CategoriesResponse;
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

        mExpandingList = findViewById(R.id.expanding_list_main);
        getAllCategories();


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


                        Log.e("pppp", mapCategory.size() + "");
                        addItem(categoriesResponses.get(i).getCategoryname(), subItems, color);
                    }


                } else

                {
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
            Toast.makeText(ChooseCategoryActivity.this, "click", Toast.LENGTH_SHORT).show();
            for (String categoryName : mapCategory.keySet()) {
                if (subTitle.equals(categoryName)) {

                    Toast.makeText(this, "category id: " + mapCategory.get(categoryName), Toast.LENGTH_SHORT).show();

                }

            }


//            if (mapCategory.get(subTitle).contains(subTitle)) {
//                Toast.makeText(this, mapCategory.containsKey(subTitle) + "", Toast.LENGTH_LONG).show();
//            }


//            for (int i = 0; i < mapCategory.size(); i++){
//
//
//
//            }

//            for (int i = 0; i < categoriesResponses.size(); i++) {
//                String selectedCategoryName = categoriesResponses.get(i).getCategorychildren().get(0).getCategoryName();
//
//
//                if (subTitle.contains(selectedCategoryName)) {
//                    int selectedId = categoriesResponses.get(i).getCategorychildren().get(0).getId();
//                    Toast.makeText(ChooseCategoryActivity.this, "category id = " + selectedId, Toast.LENGTH_LONG).show();
//                    return;
//                } else {
//                    Log.e("pppp subTitle", subTitle);
//                    Log.e("pppp selectedCategory", selectedCategoryName);
//                }
//            }

        });
    }
}
