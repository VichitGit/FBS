package vichitpov.com.fbs.ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.SellerSeeMoreAdapter;
import vichitpov.com.fbs.callback.MyOnClickListener;
import vichitpov.com.fbs.callback.OnLoadMore;
import vichitpov.com.fbs.constant.RequestCode;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.FavoriteResponse;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activities.login.StartLoginActivity;

import static vichitpov.com.fbs.adapter.SellerSeeMoreAdapter.linearLayoutManager;

public class ProductSellerCategoryActivity extends AppCompatActivity implements OnLoadMore, SwipeRefreshLayout.OnRefreshListener, MyOnClickListener {
    private String categoryId;
    private TextView textToolbar;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SellerSeeMoreAdapter adapter;
    private ImageView imageBack;
    private int totalPage;
    private int page = 1;
    private ApiService apiService;
    private TextView textNotFound;
    private UserInformationManager userInformationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_seller_category);

        initView();

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        apiService = ServiceGenerator.createService(ApiService.class);

        categoryId = getIntent().getStringExtra("CATEGORY_ID");
        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        textToolbar.setText(categoryName);

        setRecyclerView();
        loadMoreBuyerPagination(page);

        adapter.onLoadMore(this);
        adapter.mySetOnClick(this);
        refreshLayout.setOnRefreshListener(this);
        imageBack.setOnClickListener(view -> finish());


    }

    //click on Item
    @Override
    public void setOnItemClick(int position, ProductResponse.Data productResponse) {

        Intent intent = new Intent(this, DetailProductActivity.class);
        intent.putExtra("productList", productResponse);
        startActivity(intent);

    }

    //click on view
    @Override
    public void setOnViewClick(int position, int id, View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.inflate(R.menu.menu_popup_menu);
        popup.show();
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.popFavorite) {
                String accessToken = userInformationManager.getUser().getAccessToken();
                if (accessToken.equals("N/A")) {
                    startActivity(new Intent(getApplicationContext(), StartLoginActivity.class));
                    finish();
                } else {
                    addFavorite(accessToken, id);
                }

            } else if (item.getItemId() == R.id.popNotification) {
                Toast.makeText(this, "Send notification to user", Toast.LENGTH_SHORT).show();
            }
            return false;
        });

    }

    private void addFavorite(String accessToken, int id) {
        Call<FavoriteResponse> call = apiService.addFavorite(accessToken, id);
        call.enqueue(new Callback<FavoriteResponse>() {
            @Override
            public void onResponse(@NonNull Call<FavoriteResponse> call, @NonNull Response<FavoriteResponse> response) {
                if (response.isSuccessful()) {
                    Log.e("pppp success", response.body().getData().toString());
                    Toast.makeText(getApplicationContext(), "Added favorite", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("pppp else", response.code() + " = " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<FavoriteResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("pppp", "onFailure: " + t.getMessage());

            }
        });

    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }

    //when recycler scroll down, this method with invoke
    @Override
    public void setOnLoadMore() {
        if (page == totalPage) {
            return;
        }
        adapter.addProgressBar();
        loadMoreBuyerPagination(++page);
    }

    //request data
    private void loadMoreBuyerPagination(int page) {
        textNotFound.setVisibility(View.GONE);

        if (page == 1) {
            progressBar.setIndeterminate(true);
        } else {
            progressBar.setIndeterminate(false);
        }


        Call<ProductResponse> call = apiService.getProductByCategory(Integer.parseInt(categoryId), page);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductResponse> call, @NonNull final Response<ProductResponse> response) {

                if (response.isSuccessful()) {
                    totalPage = response.body().getMeta().getLastPage();
                    if (adapter.isLoading()) {
                        if (adapter.getItemCount() > 0) {
                            adapter.removeProgressBar();
                        }
                    }

                    List<ProductResponse.Data> productList = response.body().getData();
                    if (productList != null) {
                        if (productList.size() == 0) {
                            textNotFound.setVisibility(View.VISIBLE);
                            Toast.makeText(ProductSellerCategoryActivity.this, "null", Toast.LENGTH_SHORT).show();
                        } else {
                            adapter.addMoreItems(productList);
                        }
                    }
                    adapter.onLoaded();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Log.e("pppp", "else " + response.code() + " = " + response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("pppp", t.getMessage());
            }
        });

    }


    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SellerSeeMoreAdapter(this, recyclerView, linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        textNotFound = findViewById(R.id.textNotFound);
        textToolbar = findViewById(R.id.textCategory);
        imageBack = findViewById(R.id.imageBack);
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progressBar);
        refreshLayout = findViewById(R.id.swipeRefresh);

    }

}
