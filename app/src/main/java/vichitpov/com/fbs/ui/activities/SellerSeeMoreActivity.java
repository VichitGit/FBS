package vichitpov.com.fbs.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.SellerSeeMoreAdapter;
import vichitpov.com.fbs.base.BaseAppCompatActivity;
import vichitpov.com.fbs.callback.OnLoadMore;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;

import static vichitpov.com.fbs.adapter.SellerSeeMoreAdapter.linearLayoutManager;

public class SellerSeeMoreActivity extends BaseAppCompatActivity implements OnLoadMore, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SellerSeeMoreAdapter adapter;
    private ImageView imageBack;
    private int totalPage;
    private int page = 1;
    private ApiService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_see_more);

        initView();
        apiService = ServiceGenerator.createService(ApiService.class);

        setRecyclerView();
        loadMoreBuyerPagination(page);

        adapter.onLoadMore(this);
        refreshLayout.setOnRefreshListener(this);
        imageBack.setOnClickListener(view -> finish());
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
        if (page == 1) {
            progressBar.setIndeterminate(true);
        } else {
            progressBar.setIndeterminate(false);
        }

        Call<ProductResponse> call = apiService.seeMoreSellerLoadByPagination(page);
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
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
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

        imageBack = findViewById(R.id.imageBack);
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progressBar);
        refreshLayout = findViewById(R.id.swipeRefresh);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
