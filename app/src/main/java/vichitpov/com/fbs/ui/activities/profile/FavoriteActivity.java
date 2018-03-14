package vichitpov.com.fbs.ui.activities.profile;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.SellerSeeMoreAdapter;
import vichitpov.com.fbs.callback.MyOnClickListener;
import vichitpov.com.fbs.callback.OnLoadMore;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activities.login.StartLoginActivity;

import static vichitpov.com.fbs.adapter.SellerSeeMoreAdapter.linearLayoutManager;

public class FavoriteActivity extends AppCompatActivity implements OnLoadMore, MyOnClickListener {
    private UserInformationManager userInformationManager;
    private SwipeRefreshLayout refreshLayout;
    private SellerSeeMoreAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ApiService apiService;
    private TextView textNotFound;
    private ImageView imageBack;
    private int totalPage;
    private int page = 1;

    private NiftyDialogBuilder dialogBuilder;
    private List<Integer> favoriteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        initView();
        apiService = ServiceGenerator.createService(ApiService.class);
        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        dialogBuilder = NiftyDialogBuilder.getInstance(this);

        setRecyclerView();
        loadMoreBuyerPagination(userInformationManager.getUser().getAccessToken(), page);

        adapter.onLoadMore(this);
        adapter.mySetOnClick(this);
        imageBack.setOnClickListener(view -> finish());
        refreshLayout.setOnRefreshListener(() -> refreshLayout.setRefreshing(false));

    }

    @Override
    public void setOnItemClick(int position, ProductResponse.Data productResponse) {

    }

    @Override
    public void setOnViewClick(int position, int id, View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.inflate(R.menu.menu_popup_menu);

        for (int i = 0; i < favoriteList.size(); i++) {
            if (id == favoriteList.get(i)) {
                popup.getMenu().findItem(R.id.popFavorite).setVisible(false);
                popup.getMenu().findItem(R.id.popRemoveFavorite).setVisible(true);
                break;
            } else {
                popup.getMenu().findItem(R.id.popFavorite).setVisible(true);
                popup.getMenu().findItem(R.id.popRemoveFavorite).setVisible(false);
            }
        }


        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.popFavorite) {
                String accessToken = userInformationManager.getUser().getAccessToken();
                if (accessToken.equals("N/A")) {
                    startActivity(new Intent(getApplicationContext(), StartLoginActivity.class));
                    finish();
                } else {
                    dialogBuilder
                            .withTitle("Add to favorite!")
                            .withMessage("Do you want to add this product to your favorite?")
                            .withTitleColor(Color.WHITE)
                            .withMessageColor(Color.WHITE)
                            .withDialogColor("#8cff3737")
                            .withButton1Text("YES")
                            .withButton2Text("NO")
                            .isCancelableOnTouchOutside(false)
                            .setButton1Click(view1 -> {
                                //delete funtion
                                dialogBuilder.dismiss();
                            })
                            .setButton2Click(view12 -> dialogBuilder.dismiss())
                            .show();
                }

            } else if (item.getItemId() == R.id.popRemoveFavorite) {
                Toast.makeText(this, "Remove favorite", Toast.LENGTH_SHORT).show();

            } else if (item.getItemId() == R.id.popNotification) {
                Toast.makeText(this, "Send notification to user", Toast.LENGTH_SHORT).show();
            }
            return false;
        });

        popup.show();

    }

    @Override
    public void setOnLoadMore() {
        if (page == totalPage) {
            return;
        }
        adapter.addProgressBar();
        loadMoreBuyerPagination(userInformationManager.getUser().getAccessToken(), ++page);

    }

    //request data
    private void loadMoreBuyerPagination(String accessToken, int page) {
        textNotFound.setVisibility(View.GONE);

        if (page == 1) {
            progressBar.setIndeterminate(true);
        } else {
            progressBar.setIndeterminate(false);
        }


        Call<ProductResponse> call = apiService.getAllUserFavorite(accessToken, page);
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
                        if (productList.size() != 0) {
                            adapter.addMoreItems(productList);
                        } else {
                            textNotFound.setVisibility(View.VISIBLE);
                        }
                    }
                    adapter.onLoaded();
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    textNotFound.setVisibility(View.VISIBLE);
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
        textNotFound = findViewById(R.id.textNotFound);
        textNotFound.setVisibility(View.GONE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
