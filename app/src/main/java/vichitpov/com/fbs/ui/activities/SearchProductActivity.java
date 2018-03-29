package vichitpov.com.fbs.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.SearchAdapter;
import vichitpov.com.fbs.base.BaseAppCompatActivity;
import vichitpov.com.fbs.base.InternetConnection;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;

public class SearchProductActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private TextView textNotFound, textCountResult;
    private LinearLayout linearResult;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private int totalPage;
    private int page = 1;
    private Call<ProductResponse> call;
    private SearchAdapter adapter;
    private ApiService apiService;
    private String submitText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        linearResult = findViewById(R.id.linearResult);
        textCountResult = findViewById(R.id.textCount);
        linearResult.setVisibility(View.GONE);

        ImageView imageBack = findViewById(R.id.image_back);
        ImageView imageFilter = findViewById(R.id.imageFilter);
        SearchView searchView = findViewById(R.id.search_product);

        ImageView searchViewIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        ViewGroup linearLayoutSearchView = (ViewGroup) searchViewIcon.getParent();
        linearLayoutSearchView.removeView(searchViewIcon);

        recyclerView = findViewById(R.id.recycler);
        textNotFound = findViewById(R.id.textNotFound);
        progressBar = findViewById(R.id.progress);
        apiService = ServiceGenerator.createService(ApiService.class);

        setRecyclerView();

        //search event
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
        // scroll end of item recyclerview, this method will invork.
        adapter.onSearchLoadMore(() -> {

            if (page > totalPage) {
                return;
            }
            if (adapter.getItemCount() > 0) {
                adapter.addProgressBar();
            }
            searchProduct(submitText, ++page);

        });

        //listener
        imageBack.setOnClickListener(this);
        imageFilter.setOnClickListener(this);

    }

    private void showDialogChooseOptionSearch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search by: ");

        builder.setItems(new CharSequence[]{"All Product", "Seller", "Buyer"}, (dialog, which) -> {
            if (which == 0) {
                Toast.makeText(this, "Next version.", Toast.LENGTH_SHORT).show();
            }


        });
        builder.show();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.image_back:
                finish();
                break;
            case R.id.imageFilter:
                showDialogChooseOptionSearch();
                break;

        }
    }

    //search product by page, default page 1(start search)
    private void searchProduct(String keyword, int page) {
        if (InternetConnection.isNetworkConnected(this)) {
            call = apiService.searchAll(keyword, page);
            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProductResponse> call, @NonNull final Response<ProductResponse> response) {
                    if (response.isSuccessful()) {
                        linearResult.setVisibility(View.VISIBLE);
                        totalPage = response.body().getMeta().getLastPage();

                        if (adapter.isLoading()) {
                            if (adapter.getItemCount() > 0) {
                                adapter.removeProgressBar();
                            }
                        }

                        List<ProductResponse.Data> productList = response.body().getData();

                        if (productList != null) {
                            if (productList.size() == 0) {
                                Log.e("pppp", "if == 0");
                                textNotFound.setVisibility(View.VISIBLE);
                                textNotFound.setText("Not Found");
                            }

                            textNotFound.setVisibility(View.GONE);
                            adapter.addMoreItems(productList);

                        } else {
                            Log.e("pppp", "else == null");
                            textNotFound.setText("Not Found");
                            textNotFound.setVisibility(View.VISIBLE);
                        }

                        textCountResult.setText(submitText);
                        progressBar.setVisibility(View.GONE);
                        adapter.onLoaded();


                    } else {
                        linearResult.setVisibility(View.VISIBLE);
                        textCountResult.setText("Not Found");
                        Log.e("pppp else", response.code() + " = " + response.message());
                        displayView(false);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    //Log.e("pppp else", t.getMessage());
                    if (!call.isCanceled()) {
                        Toast.makeText(getApplicationContext(), "Server Problem", Toast.LENGTH_SHORT).show();
                        linearResult.setVisibility(View.VISIBLE);
                        textCountResult.setText("Not Found");
                        textNotFound.setText("Not Found");
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            linearResult.setVisibility(View.VISIBLE);
            textCountResult.setText(submitText);
            progressBar.setVisibility(View.GONE);
            textNotFound.setText("No internect connection!");
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
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
