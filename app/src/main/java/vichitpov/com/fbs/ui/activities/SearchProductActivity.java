package vichitpov.com.fbs.ui.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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


import vichitpov.com.fbs.adapter.SearchAdapter;
import vichitpov.com.fbs.base.InternetConnection;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;

public class SearchProductActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textNotFound;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private int totalPage;
    private int page = 1;
    private Call<ProductResponse> call;
    private SearchAdapter adapter;
    private ApiService apiService;
    private String submitText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        ImageView imageBack = findViewById(R.id.image_back);
        SearchView searchView = findViewById(R.id.search_product);
        recyclerView = findViewById(R.id.recycler);
        textNotFound = findViewById(R.id.textNotFound);
        progressBar = findViewById(R.id.progress);
        apiService = ServiceGenerator.createService(ApiService.class);

        setRecyclerView();

        imageBack.setOnClickListener(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.clearList();
                displayView(true);
                page = 1;

                if (call != null) {
                    call.cancel();
                }

                if (s.isEmpty()) {
                    displayView(false);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    submitText = s;
                    searchProduct(s, page);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        adapter.onSearchLoadMore(() -> {
            Log.e("pppp", page + " = " + totalPage);

            if (page > totalPage) {
                return;
            }
            if (adapter.getItemCount() > 0) {
                adapter.addProgressBar();
            }
            searchProduct(submitText, ++page);

        });

    }


    @Override
    public void onClick(View view) {
        finish();
    }


    private void searchProduct(String keyword, int page) {
        if (InternetConnection.isNetworkConnected(this)) {
            call = apiService.searchAll(keyword, page);
            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProductResponse> call, @NonNull final Response<ProductResponse> response) {
                    if (response.isSuccessful()) {
                        totalPage = response.body().getMeta().getLastPage();
                        if (adapter.isLoading()) {
                            //Log.e("pppp", "isLoading");
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
                        displayView(false);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Log.e("pppp else", t.getMessage());
                    if (!call.isCanceled()) {
                        Toast.makeText(getApplicationContext(), "Server Problem", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SearchAdapter(this, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    private void displayView(boolean visible) {
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
            textNotFound.setVisibility(View.GONE);
        } else {
            textNotFound.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
