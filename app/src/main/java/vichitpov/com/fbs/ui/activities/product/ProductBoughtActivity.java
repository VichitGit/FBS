package vichitpov.com.fbs.ui.activities.product;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.BuyerSeeMoreAdapter;
import vichitpov.com.fbs.base.InternetConnection;
import vichitpov.com.fbs.base.Retrofit;
import vichitpov.com.fbs.callback.OnClickDelete;
import vichitpov.com.fbs.callback.OnClickEdit;
import vichitpov.com.fbs.callback.OnLoadMore;
import vichitpov.com.fbs.constant.AnyConstant;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activities.EditProductBuyActivity;
import vichitpov.com.fbs.ui.activities.login.StartLoginActivity;

import static vichitpov.com.fbs.adapter.BuyerSeeMoreAdapter.PRODUCT_POSTED_BUY;

public class ProductBoughtActivity extends AppCompatActivity implements View.OnClickListener, OnLoadMore, SwipeRefreshLayout.OnRefreshListener, OnClickDelete, OnClickEdit {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private BuyerSeeMoreAdapter adapter;
    private int totalPage;
    private int page = 1;
    private int selectPositionProduct;
    private UserInformationManager userInformationManager;
    private NiftyDialogBuilder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_bought);

        ImageView imageBack = findViewById(R.id.image_back);
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progress);
        refreshLayout = findViewById(R.id.swipeRefresh);

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        dialogBuilder = NiftyDialogBuilder.getInstance(this);

        setRecyclerView();
        loadMoreBuyerPagination(page);

        imageBack.setOnClickListener(this);
        adapter.onLoadMore(this);
        adapter.setOnDeleteClick(this);
        adapter.setOnEditClick(this);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AnyConstant.EDIT_PRODUCT_BUY_CODE && resultCode != RESULT_CANCELED) {
            adapter.updatedItem(selectPositionProduct, (ProductResponse.Data) data.getSerializableExtra(AnyConstant.PRODUCT_LIST));

        }
    }

    //click view detail
    @Override
    public void setOnClick(int id, int position) {
        if (userInformationManager.getUser().getAccessToken().equals("N/A")) {
            startActivity(new Intent(this, StartLoginActivity.class));
        } else {
            if (InternetConnection.isNetworkConnected(this)) {
                dialogBuilder
                        .withTitle(getString(R.string.text_delete))
                        .withMessage(R.string.text_question_to_delete)
                        .withTitleColor(Color.WHITE)
                        .withMessageColor(Color.WHITE)
                        .withDialogColor("#8cff3737")
                        .withButton1Text(getString(R.string.button_yes))
                        .withButton2Text(getString(R.string.button_no))
                        .isCancelableOnTouchOutside(false)
                        .setButton1Click(view1 -> {
                            Retrofit.deleteUserPost(this, userInformationManager.getUser().getAccessToken(), id);
                            adapter.refreshData(position);
                            dialogBuilder.dismiss();
                        })
                        .setButton2Click(view12 -> dialogBuilder.dismiss())
                        .show();
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        }

    }

    //click edit product
    @Override
    public void setOnClickEdit(int position, ProductResponse.Data productResponse) {
        selectPositionProduct = position;
        Intent intent = new Intent(this, EditProductBuyActivity.class);
        intent.putExtra(AnyConstant.PRODUCT_LIST, productResponse);
        startActivityForResult(intent, AnyConstant.EDIT_PRODUCT_BUY_CODE);
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
