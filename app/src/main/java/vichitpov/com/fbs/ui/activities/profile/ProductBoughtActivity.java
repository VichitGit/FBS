package vichitpov.com.fbs.ui.activities.profile;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.BuyerSeeMoreAdapter;
import vichitpov.com.fbs.callback.OnLoadMore;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;

import static vichitpov.com.fbs.adapter.BuyerSeeMoreAdapter.PRODUCT_POSTED_BUY;

public class ProductBoughtActivity extends AppCompatActivity implements View.OnClickListener, OnLoadMore, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private BuyerSeeMoreAdapter adapter;
    private int totalPage;
    private int page = 1;
    private UserInformationManager userInformationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_bought);

        ImageView imageBack = findViewById(R.id.image_back);
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progress);
        refreshLayout = findViewById(R.id.swipeRefresh);

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));

        setRecyclerView();
        loadMoreBuyerPagination(page);

        imageBack.setOnClickListener(this);
        adapter.onLoadMore(this);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }


    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void setOnLoadMore() {
        if (page == totalPage) {
            return;
        }
        adapter.addProgressBar();
        loadMoreBuyerPagination(++page);
    }

    private void loadMoreBuyerPagination(int page) {
        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        String accessToken = userInformationManager.getUser().getAccessToken();
        if (page == 1) {
            progressBar.setIndeterminate(true);
        } else {
            progressBar.setIndeterminate(false);
        }

        Call<ProductResponse> call = apiService.getProductBuySpecificUser(accessToken, page);
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
                        adapter.addMoreItems(productList);
                    }
                    adapter.onLoaded();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Log.e("pppp else", response.code() + " = " + response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BuyerSeeMoreAdapter(this, recyclerView, PRODUCT_POSTED_BUY);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
