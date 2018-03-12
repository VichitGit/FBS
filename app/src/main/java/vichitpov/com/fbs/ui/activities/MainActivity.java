package vichitpov.com.fbs.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;
import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.CategoryHeaderAdapter;
import vichitpov.com.fbs.adapter.FavoriteAdapter;
import vichitpov.com.fbs.adapter.RecentlySingleBuyerAdapter;
import vichitpov.com.fbs.adapter.RecentlySingleSellerAdapter;
import vichitpov.com.fbs.base.BaseAppCompatActivity;
import vichitpov.com.fbs.base.IntentData;
import vichitpov.com.fbs.base.InternetConnection;
import vichitpov.com.fbs.callback.MyOnClickListener;
import vichitpov.com.fbs.callback.OnClickSingle;
import vichitpov.com.fbs.model.CategoryHeaderModel;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.CategoriesResponse;
import vichitpov.com.fbs.retrofit.response.FavoriteResponse;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.response.UserInformationResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activities.login.StartLoginActivity;
import vichitpov.com.fbs.ui.activities.profile.UserProfileActivity;

public class MainActivity extends BaseAppCompatActivity implements MyOnClickListener, OnClickSingle {

    private RecyclerView recyclerCategoryHeader, recyclerRecentlyBuyer, recyclerRecentSeller, recyclerFavorite;
    private RecyclerView.LayoutManager layoutManager;
    private ApiService apiService;
    private SwipeRefreshLayout refreshLayout;
    private TextView textProfile, textSearch, seeMoreSeller, seeMoreBuyer, textUpload;
    private FloatingActionButton floatingScroll;
    private ScrollView scrollView;
    private RelativeLayout relativeRecentlySeller, relativeRecentlyBuyer, relativeFavorite;
    private ProgressBar progressBar;
    private LinearLayout linearInternetUnavailable;
    private RecentlySingleBuyerAdapter adapterBuyer;
    private RecentlySingleSellerAdapter adapterSeller;
    private FavoriteAdapter favoriteAdapter;
    private UserInformationManager userInformationManager;
    private boolean isInformationLoadSuccess;
    private SpotsDialog dialog;
    private BannerSlider bannerSlider;
    private CategoryHeaderAdapter categoryHeaderAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = ServiceGenerator.createService(ApiService.class);
        adapterSeller = new RecentlySingleSellerAdapter(getApplicationContext());
        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        dialog = new SpotsDialog(this, "Updating...");

        Log.e("pppp", userInformationManager.getUser().getAccessToken());

        initView();
        setUpSliderHeader();
        setUpCategoryHeader();

        if (InternetConnection.isNetworkConnected(this)) {
            isInformationLoadSuccess = true;
            linearInternetUnavailable.setVisibility(View.GONE);
            setFavorite();
            setUpRecentlyBuyer();
            setUpRecentlySeller();
            getInformationUser();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            isInformationLoadSuccess = false;
            linearInternetUnavailable.setVisibility(View.VISIBLE);
        }

        eventListener();
        adapterSeller.setOnCLickListener(this);
        categoryHeaderAdapter.setOnClickListener(this);


    }

    //load to get information user, it's means that refresh user data and delete old share preference
    //and add new share preference(reason cuz when user upload product we cannot update share preference
    //so if we cannot upload share preference cannot upload too. so when we go to user profile, it's will
    //show old information. so we need to update share preference to get new information in local mobile:D
    private void getInformationUser() {
        if (!isInformationLoadSuccess) {
            dialog.show();
        }
        String accessToken = userInformationManager.getUser().getAccessToken();
        if (!userInformationManager.getUser().getAccessToken().equals("N/A")) {
            Call<UserInformationResponse> call = apiService.getUserInformation(accessToken);
            call.enqueue(new Callback<UserInformationResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserInformationResponse> call, @NonNull Response<UserInformationResponse> response) {
                    if (response.isSuccessful()) {
                        userInformationManager.deleteUserInformation();
                        userInformationManager.saveInformation(response.body());
                        isInformationLoadSuccess = true;

                    } else if (response.code() == 401) {
                        isInformationLoadSuccess = false;

                    } else {
                        isInformationLoadSuccess = false;
                    }

                    if (!isInformationLoadSuccess) {
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserInformationResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    if (!isInformationLoadSuccess) {
                        dialog.dismiss();
                    }
                    isInformationLoadSuccess = false;
                }
            });
        }

    }

    @Override
    public void setOnItemClick(int position, ProductResponse.Data productResponse) {
        Intent intent = new Intent(this, DetailProductActivity.class);
        intent.putExtra("productList", productResponse);
        startActivity(intent);

    }

    @Override
    public void setOnViewClick(int position, int id, View view) {
        PopupMenu popup = new PopupMenu(MainActivity.this, view);
        popup.inflate(R.menu.menu_popup_menu);
        popup.show();
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.popFavorite) {
                Toast.makeText(MainActivity.this, "Add to favorite", Toast.LENGTH_SHORT).show();
            }
            return false;
        });
    }

    //event click listener to category to activity category
    @Override
    public void setOnClick() {
        Intent intent = new Intent(this, ChooseCategoryActivity.class);
        intent.putExtra(IntentData.SEND_FROM_MAIN_ACTIVITY, IntentData.SEND_FROM_MAIN_ACTIVITY);
        startActivity(intent);

    }

    private void setUpSliderHeader() {
        List<Banner> bannersList = new ArrayList<>();
        bannersList.add(new RemoteBanner("https://s3.envato.com/files/228473424/590x300.jpg"));
        bannersList.add(new RemoteBanner("https://kalidas365itsolutions.files.wordpress.com/2014/06/every-sale.jpg"));
        bannersList.add(new RemoteBanner("https://kalidas365itsolutions.files.wordpress.com/2014/06/seller-verification.jpg"));
        bannersList.add(new RemoteBanner("https://microlancer.lancerassets.com/v2/services/3f/c593d0437011e6ac7853977e2e0bdc/large__original_1.jpg"));
        bannersList.add(new RemoteBanner("http://alphatec.in/images/slider/ecommerce-website-development.jpg"));
        bannersList.add(new RemoteBanner("http://www.gogits.com/images/slider3.jpg"));
        bannerSlider.setBanners(bannersList);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void eventListener() {

        textProfile.setOnClickListener(view -> {
            if (!userInformationManager.getUser().getAccessToken().equals("N/A")) {
                if (isInformationLoadSuccess) {
                    startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                } else {
                    getInformationUser();
                }
            } else {
                startActivity(new Intent(getApplicationContext(), StartLoginActivity.class));
            }
        });


        textSearch.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SearchProductActivity.class)));
        textUpload.setOnClickListener(view -> {
            if (isAccessTokenAvailable()) {
                dialogBottom();
            }
        });
//        scrollView.setOnScrollChangeListener((view, i, i1, i2, i3) -> floatingScroll.setVisibility(View.VISIBLE));

        floatingScroll.setOnClickListener(view -> scrollView.fullScroll(ScrollView.FOCUS_UP));
        seeMoreBuyer.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), BuyerSeeMoreActivity.class)));
        seeMoreSeller.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SellerSeeMoreActivity.class)));
        refreshLayout.setOnRefreshListener(() -> refreshLayout.setRefreshing(false));

    }

    private boolean isAccessTokenAvailable() {
        if (!userInformationManager.getUser().getAccessToken().equals("N/A")) {
            return true;
        } else {
            Intent intent = new Intent(this, StartLoginActivity.class);
            startActivity(intent);
            return false;
        }
    }

    private void setUpCategoryHeader() {
        layoutManager = new GridLayoutManager(MainActivity.this, 3);
        recyclerCategoryHeader.setLayoutManager(layoutManager);

        List<CategoryHeaderModel> modelList = new ArrayList<>();
        modelList.add(new CategoryHeaderModel("Setting", R.drawable.ic_setting_background));
        modelList.add(new CategoryHeaderModel("Favorite", R.drawable.ic_star_background));
        modelList.add(new CategoryHeaderModel("Categories", R.drawable.ic_category_background));

        categoryHeaderAdapter = new CategoryHeaderAdapter(getApplicationContext(), modelList);
        recyclerCategoryHeader.setAdapter(categoryHeaderAdapter);


    }

    private void setUpRecentlyBuyer() {
        layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false);
        recyclerRecentlyBuyer.setLayoutManager(layoutManager);
        progressBar.setVisibility(View.VISIBLE);

        Call<ProductResponse> call = apiService.singlePageBuyer();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                if (response.isSuccessful()) {

                    adapterBuyer = new RecentlySingleBuyerAdapter(getApplicationContext(), response.body().getData());
                    recyclerRecentlyBuyer.setAdapter(adapterBuyer);

                    progressBar.setVisibility(View.GONE);
                    relativeRecentlyBuyer.setVisibility(View.VISIBLE);

                } else {
                    Log.e("pppp", response.code() + " = " + response.message());
                    progressBar.setVisibility(View.GONE);
                    relativeRecentlyBuyer.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                progressBar.setVisibility(View.GONE);
                relativeRecentlyBuyer.setVisibility(View.GONE);


            }
        });


    }

    private void setUpRecentlySeller() {

        layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        recyclerRecentSeller.setLayoutManager(layoutManager);
        relativeRecentlySeller.setVisibility(View.GONE);

        Call<ProductResponse> call = apiService.singlePageSeller();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                if (response.isSuccessful()) {

                    adapterSeller.addItem(response.body().getData());
                    recyclerRecentSeller.setAdapter(adapterSeller);
                    relativeRecentlySeller.setVisibility(View.VISIBLE);


                } else {

                    relativeRecentlySeller.setVisibility(View.GONE);
                    Log.e("pppp", response.code() + " = " + response.message());

                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {

                t.printStackTrace();
                Log.e("pppp", t.getMessage());

                relativeRecentlySeller.setVisibility(View.GONE);

            }
        });
    }

    private void setFavorite() {
        if (!userInformationManager.getUser().getAccessToken().equals("N/A")) {
            //layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);

            recyclerFavorite.setLayoutManager(layoutManager);
            favoriteAdapter = new FavoriteAdapter(this);

            Call<FavoriteResponse> call = apiService.topFavorite(userInformationManager.getUser().getAccessToken());
            call.enqueue(new Callback<FavoriteResponse>() {
                @Override
                public void onResponse(@NonNull Call<FavoriteResponse> call, @NonNull Response<FavoriteResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Log.e("ppppp", "success: " + response.body().getData().toString());
                            relativeFavorite.setVisibility(View.VISIBLE);
                            favoriteAdapter.addItem(response.body().getData());
                            recyclerFavorite.setAdapter(favoriteAdapter);
                        } else if (response.body().getData().size() == 0) {
                            relativeFavorite.setVisibility(View.GONE);
                        }
                    } else {
                        relativeFavorite.setVisibility(View.GONE);
                        Log.e("ppppp", "else: " + response.code() + " = " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FavoriteResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Log.e("ppppp", "onFailure: " + t.getMessage());
                    relativeFavorite.setVisibility(View.GONE);
                }
            });
        } else {
            relativeFavorite.setVisibility(View.GONE);
        }
    }

//    private void getAllCategories() {
//        ApiService apiService = ServiceGenerator.createService(ApiService.class);
//        Call<CategoriesResponse> call = apiService.getAllCategories();
//        call.enqueue(new Callback<CategoriesResponse>() {
//            @SuppressLint("UseSparseArrays")
//            @Override
//            public void onResponse(@NonNull Call<CategoriesResponse> call, @NonNull Response<CategoriesResponse> response) {
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//                        categoriesResponse = response.body();
//
//
//                    }
//                } else {
//                    Log.e("pppp", response.code() + " = " + response.message());
//                }
//
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<CategoriesResponse> call, @NonNull Throwable t) {
//                t.printStackTrace();
//                Log.e("pppp", t.getMessage());
//            }
//        });
//
//
//    }

    private void dialogBottom() {
        @SuppressLint("ResourceAsColor") BottomSheetMenuDialog dialog = new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setIconTintColorResource(R.color.colorPrimary)
                .setItemTextColor(R.color.colorPrimary)
                .setMenu(R.menu.menu_dialog_post)
                .setItemClickListener(item -> {
                    if (item.getItemId() == R.id.dialog_bottom_post_to_buy) {
                        startActivity(new Intent(getApplicationContext(), PostToBuyActivity.class));
                    } else if (item.getItemId() == R.id.dialog_bottom_post_to_sell) {
                        startActivity(new Intent(getApplicationContext(), PostToSellActivity.class));
                    }

                })
                .createDialog();
        dialog.show();
    }

    private void initView() {

        recyclerCategoryHeader = findViewById(R.id.recyclerCategories);
        recyclerRecentlyBuyer = findViewById(R.id.recyclerRecentlyBuyer);
        recyclerRecentSeller = findViewById(R.id.recyclerRecentlySeller);
        recyclerFavorite = findViewById(R.id.recyclerFavorite);
        linearInternetUnavailable = findViewById(R.id.linearInternetUnavailable);
        refreshLayout = findViewById(R.id.swipeRefresh);

        textProfile = findViewById(R.id.textProfile);
        textSearch = findViewById(R.id.textSearch);
        seeMoreBuyer = findViewById(R.id.textSeeMoreBuyer);
        seeMoreSeller = findViewById(R.id.textSeeMoreSeller);
        textUpload = findViewById(R.id.textUpload);

        floatingScroll = findViewById(R.id.floatingScroll);
        scrollView = findViewById(R.id.scrollView);

        progressBar = findViewById(R.id.progressBar);
        relativeRecentlySeller = findViewById(R.id.relativeRecentlySeller);
        relativeRecentlyBuyer = findViewById(R.id.relativeRecentlyBuyer);
        relativeFavorite = findViewById(R.id.relativeFavorite);
        bannerSlider = findViewById(R.id.bannerSlider);

        progressBar.setVisibility(View.GONE);
        relativeRecentlySeller.setVisibility(View.GONE);
        relativeRecentlyBuyer.setVisibility(View.GONE);
        relativeFavorite.setVisibility(View.GONE);
        floatingScroll.setVisibility(View.GONE);
        linearInternetUnavailable.setVisibility(View.GONE);


    }


}
















