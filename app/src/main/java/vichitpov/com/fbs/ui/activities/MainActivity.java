package vichitpov.com.fbs.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.CategoryHeaderAdapter;
import vichitpov.com.fbs.adapter.FavoriteAdapter;
import vichitpov.com.fbs.adapter.RecentlySingleBuyerAdapter;
import vichitpov.com.fbs.adapter.RecentlySingleSellerAdapter;
import vichitpov.com.fbs.base.BaseAppCompatActivity;
import vichitpov.com.fbs.base.InternetConnection;
import vichitpov.com.fbs.callback.OnClickSingle;
import vichitpov.com.fbs.constant.AnyConstant;
import vichitpov.com.fbs.model.CategoryHeaderModel;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.response.UserInformationResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activities.login.StartLoginActivity;
import vichitpov.com.fbs.ui.activities.profile.FavoriteActivity;
import vichitpov.com.fbs.ui.activities.profile.UserProfileActivity;

public class MainActivity extends BaseAppCompatActivity implements OnClickSingle {

    private RecyclerView recyclerCategoryHeader, recyclerRecentlyBuyer, recyclerRecentSeller, recyclerFavorite, recyclerTopSell;
    private RecyclerView.LayoutManager layoutManager;
    private ApiService apiService;
    private SwipeRefreshLayout refreshLayout;
    private TextView textProfile, textSearch, seeMoreSeller, seeMoreBuyer, textUpload, textMoreFavorite;
    private FloatingActionButton floatingScroll;
    private ScrollView scrollView;
    private RelativeLayout relativeRecentlySeller, relativeRecentlyBuyer, relativeFavorite, relativeTopSell;
    private ProgressBar progressBar;
    private LinearLayout linearInternetUnavailable;
    private RecentlySingleBuyerAdapter adapterBuyer;
    private RecentlySingleSellerAdapter adapterSeller;
    private FavoriteAdapter favoriteAdapter, adapterTopSell;
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
            setUpTopSeller();
            setUpRecentlySeller();
            getInformationUser();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            isInformationLoadSuccess = false;
            linearInternetUnavailable.setVisibility(View.VISIBLE);
        }

        eventListener();
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

    //event click listener to category to activity category
    @Override
    public void setOnClick() {
        Intent intent = new Intent(this, ChooseCategoryActivity.class);
        intent.putExtra(AnyConstant.SEND_FROM_MAIN_ACTIVITY, AnyConstant.SEND_FROM_MAIN_ACTIVITY);
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
        textMoreFavorite.setOnClickListener(view -> {
            if (userInformationManager.getUser().getAccessToken().equals("N/A")) {
                startActivity(new Intent(getApplicationContext(), StartLoginActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
            }
        });
//        scrollView.setOnScrollChangeListener((view, i, i1, i2, i3) -> floatingScroll.setVisibility(View.VISIBLE));

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//                if (view.getTop() == i1) {
//                    Log.d("pppp", "MyScrollView: getTop");
//                }

                int diff = (view.getBottom() - (view.getHeight() + view.getScrollY() + view.getTop()));

                if (diff <= 0) {
                    Log.d("pppp", "MyScrollView: Bottom has been reached");
                }else{
                    Log.d("pppp", "MyScrollView: else");
                }
            }
        });

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

    //set choose type
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

    //create data with recently 14 item buy
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

    //create data with recently 14 item sell
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

    //create sell top data, validate by count view
    private void setUpTopSeller() {
        layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false);
        recyclerTopSell.setLayoutManager(layoutManager);
        adapterTopSell = new FavoriteAdapter(this);
        recyclerTopSell.setAdapter(adapterTopSell);
        relativeTopSell.setVisibility(View.GONE);

        Call<ProductResponse> call = apiService.topSellList("sells");
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    adapterTopSell.addItem(response.body().getData());
                    relativeTopSell.setVisibility(View.VISIBLE);
                } else {
                    relativeTopSell.setVisibility(View.GONE);
                    Log.e("pppp", "top: " + response.code() + " = " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                relativeTopSell.setVisibility(View.GONE);
                Log.e("pppp", "onFailure: " + t.getMessage());

            }
        });


    }

    //create user favorite, if user logged
    private void setFavorite() {
        if (!userInformationManager.getUser().getAccessToken().equals("N/A")) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);

            recyclerFavorite.setLayoutManager(layoutManager);
            favoriteAdapter = new FavoriteAdapter(this);

            Call<ProductResponse> call = apiService.getAllUserFavorite(userInformationManager.getUser().getAccessToken(), 1);
            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            relativeFavorite.setVisibility(View.VISIBLE);
                            favoriteAdapter.addItem(response.body().getData());
                            recyclerFavorite.setAdapter(favoriteAdapter);
                        } else if (response.body().getData().size() == 0) {
                            relativeFavorite.setVisibility(View.GONE);
                        }
                    } else {
                        relativeFavorite.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
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

    //dialog bottom
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
        recyclerTopSell = findViewById(R.id.recyclerTopSell);
        linearInternetUnavailable = findViewById(R.id.linearInternetUnavailable);
        refreshLayout = findViewById(R.id.swipeRefresh);

        textProfile = findViewById(R.id.textProfile);
        textSearch = findViewById(R.id.textSearch);
        seeMoreBuyer = findViewById(R.id.textSeeMoreBuyer);
        seeMoreSeller = findViewById(R.id.textSeeMoreSeller);
        textUpload = findViewById(R.id.textUpload);
        textMoreFavorite = findViewById(R.id.textSeeMoreFavorite);

        floatingScroll = findViewById(R.id.floatingScroll);
        scrollView = findViewById(R.id.scrollView);

        progressBar = findViewById(R.id.progressBar);
        relativeRecentlySeller = findViewById(R.id.relativeRecentlySeller);
        relativeRecentlyBuyer = findViewById(R.id.relativeRecentlyBuyer);
        relativeFavorite = findViewById(R.id.relativeFavorite);
        relativeTopSell = findViewById(R.id.relativeTopSell);
        bannerSlider = findViewById(R.id.bannerSlider);

        progressBar.setVisibility(View.GONE);
        relativeRecentlySeller.setVisibility(View.GONE);
        relativeRecentlyBuyer.setVisibility(View.GONE);
        relativeFavorite.setVisibility(View.GONE);
        floatingScroll.setVisibility(View.GONE);
        linearInternetUnavailable.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    protected void onStart() {
        super.onStart();
    }
}
















