package vichitpov.com.fbs.ui.activity;

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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.CategoryHeaderAdapter;
import vichitpov.com.fbs.adapter.RecentlySingleBuyerAdapter;
import vichitpov.com.fbs.adapter.RecentlySingleSellerAdapter;
import vichitpov.com.fbs.base.BaseAppCompatActivity;
import vichitpov.com.fbs.base.IntentData;
import vichitpov.com.fbs.base.InternetConnection;
import vichitpov.com.fbs.callback.MyOnClickListener;
import vichitpov.com.fbs.model.CategoryHeaderModel;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activity.profile.UserProfileActivity;

public class MainActivity extends BaseAppCompatActivity implements MyOnClickListener {

    private RecyclerView recyclerCategoryHeader, recyclerRecentlyBuyer, recyclerRecentSeller;
    private RecyclerView.LayoutManager layoutManager;
    private ApiService apiService;
    private SwipeRefreshLayout refreshLayout;
    private TextView textProfile, textSearch, seeMoreSeller, seeMoreBuyer, textUpload;
    private FlipperLayout sliderShow;
    private FloatingActionButton floatingScroll;
    private ScrollView scrollView;
    private RelativeLayout relativeRecentlySeller, relativeRecentlyBuyer;
    private ProgressBar progressBar;
    private LinearLayout linearInternetUnavailable;
    private RecentlySingleBuyerAdapter adapterBuyer;
    private RecentlySingleSellerAdapter adapterSeller;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = ServiceGenerator.createService(ApiService.class);
        adapterSeller = new RecentlySingleSellerAdapter(getApplicationContext());

        initView();
        setUpSliderHeader();
        setUpCategoryHeader();


        if (InternetConnection.isNetworkConnected(this)) {
            linearInternetUnavailable.setVisibility(View.GONE);
            setUpRecentlyBuyer();
            setUpRecentlySeller();

        } else {
            linearInternetUnavailable.setVisibility(View.VISIBLE);
        }

        eventListener();
        adapterSeller.setOnCLickListener(this);


    }

    @Override
    public void setOnItemClick(int position, List<ProductResponse.Data> productList) {
        IntentData.sendData(this, productList, position);

    }

    @Override
    public void setOnViewClick(int position, View view) {

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

    private void setUpSliderHeader() {
        int numOfPages = 3;

        String url[] = new String[]{
                "https://s3.envato.com/files/228473424/590x300.jpg",
                "https://kalidas365itsolutions.files.wordpress.com/2014/06/every-sale.jpg",
                "https://microlancer.lancerassets.com/v2/services/3f/c593d0437011e6ac7853977e2e0bdc/large__original_1.jpg",
        };

        for (int i = 0; i < numOfPages; i++) {
            FlipperView view = new FlipperView(getBaseContext());
            final int finalI = i;
            view.setImageUrl(url[i])
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP) //You can use any ScaleType
                    .setOnFlipperClickListener(flipperView -> Toast.makeText(getApplicationContext(), "Clicked: " + finalI, Toast.LENGTH_SHORT).show());

            sliderShow.setScrollTimeInSec(3);
            sliderShow.addFlipperView(view);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void eventListener() {

        textProfile.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), UserProfileActivity.class)));
        textSearch.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SearchProductActivity.class)));
        textUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBottom();
            }
        });
        scrollView.setOnScrollChangeListener((view, i, i1, i2, i3) -> floatingScroll.setVisibility(View.VISIBLE));

        floatingScroll.setOnClickListener(view -> scrollView.fullScroll(ScrollView.FOCUS_UP));
        seeMoreBuyer.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), BuyerSeeMoreActivity.class)));
        seeMoreSeller.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SellerSeeMoreActivity.class)));
        refreshLayout.setOnRefreshListener(() -> refreshLayout.setRefreshing(false));

    }

    private void setUpCategoryHeader() {
        layoutManager = new GridLayoutManager(MainActivity.this, 3);
        recyclerCategoryHeader.setLayoutManager(layoutManager);

        List<CategoryHeaderModel> modelList = new ArrayList<>();
        modelList.add(new CategoryHeaderModel("Setting", R.drawable.ic_setting_background));
        modelList.add(new CategoryHeaderModel("Favorite", R.drawable.ic_star_background));
        modelList.add(new CategoryHeaderModel("Categories", R.drawable.ic_category_background));

        CategoryHeaderAdapter adapter = new CategoryHeaderAdapter(getApplicationContext(), modelList);
        recyclerCategoryHeader.setAdapter(adapter);


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
        linearInternetUnavailable = findViewById(R.id.linearInternetUnavailable);
        refreshLayout = findViewById(R.id.swipeRefresh);

        textProfile = findViewById(R.id.textProfile);
        textSearch = findViewById(R.id.textSearch);
        seeMoreBuyer = findViewById(R.id.textSeeMoreBuyer);
        seeMoreSeller = findViewById(R.id.textSeeMoreSeller);
        textUpload = findViewById(R.id.textUpload);

        sliderShow = findViewById(R.id.headerSlideShow);
        floatingScroll = findViewById(R.id.floatingScroll);
        scrollView = findViewById(R.id.scrollView);

        progressBar = findViewById(R.id.progressBar);
        relativeRecentlySeller = findViewById(R.id.relativeRecentlySeller);
        relativeRecentlyBuyer = findViewById(R.id.relativeRecentlyBuyer);

        progressBar.setVisibility(View.GONE);
        relativeRecentlySeller.setVisibility(View.GONE);
        relativeRecentlyBuyer.setVisibility(View.GONE);
        floatingScroll.setVisibility(View.GONE);
        linearInternetUnavailable.setVisibility(View.GONE);


    }



}
















