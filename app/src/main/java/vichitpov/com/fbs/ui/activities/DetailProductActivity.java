package vichitpov.com.fbs.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.ContactAdapter;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.ui.fragment.ShowMapFragment;

public class DetailProductActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageBack;
    private BannerSlider bannerSlider;
    private Button buttonCall;
    private TextView textTitle, textDescription, textPrice, textName, textPhone, textEmail, textAddress;
    private String getPhone;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        initView();
        setUpRecycler();
        getIntentFromAnotherActivity();


        imageBack.setOnClickListener(this);
        buttonCall.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + getPhone));
            startActivity(intent);
        });
    }

    private void setUpRecycler() {
        adapter = new ContactAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    @SuppressLint("SetTextI18n")
    private void getIntentFromAnotherActivity() {
        ProductResponse.Data productResponse = (ProductResponse.Data) getIntent().getSerializableExtra("productList");
        if (productResponse != null) {
            adapter.addItem(productResponse.getContactme().getDatacontact());

            getPhone = productResponse.getContactphone();

            List<Banner> imageSliderList = new ArrayList<>();
            if (productResponse.getProductimages().size() != 0) {
                for (int i = 0; i < productResponse.getProductimages().size(); i++) {
                    imageSliderList.add(new RemoteBanner(productResponse.getProductimages().get(i)));
                }
                bannerSlider.setBanners(imageSliderList);
            }

            String priceFrom = productResponse.getPrice().get(0).getMin().substring(0, productResponse.getPrice().get(0).getMin().indexOf("."));
            String priceTo = productResponse.getPrice().get(0).getMax().substring(0, productResponse.getPrice().get(0).getMax().indexOf("."));

            textTitle.setText(productResponse.getTitle());
            textPrice.setText("Price: " + priceFrom + "$ - " + priceTo + "$");
            textDescription.setText(productResponse.getDescription());
            textAddress.setText("Contact Address: " + productResponse.getContactaddress());
            textName.setText("Contact Name: " + productResponse.getContactname());
            textPhone.setText("Contact Phone: " + productResponse.getContactphone());

            if (productResponse.getContactemail().equals("norton@null.com") || productResponse.getContactemail() == null) {
                textEmail.setVisibility(View.GONE);
            } else {
                textEmail.setVisibility(View.VISIBLE);
                textEmail.setText("Contact Email: " + productResponse.getContactemail());
            }

            if (productResponse.getContactmapcoordinate() != null) {
//                Bundle bundle = new Bundle();
//                bundle.putString("LatLng", productResponse.getContactmapcoordinate());
                ShowMapFragment showMapFragment = new ShowMapFragment();
//                showMapFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layoutShowMap, showMapFragment)
                        .commit();
            }

        }
    }

    private void initView() {
        imageBack = findViewById(R.id.image_back);
        textTitle = findViewById(R.id.textTitle);
        textDescription = findViewById(R.id.textDescription);
        textPrice = findViewById(R.id.textPrice);
        textName = findViewById(R.id.textName);
        textPhone = findViewById(R.id.textPhone);
        textEmail = findViewById(R.id.textEmail);
        textAddress = findViewById(R.id.textAddress);
        bannerSlider = findViewById(R.id.bannerSlider);
        buttonCall = findViewById(R.id.buttonCall);
        recyclerView = findViewById(R.id.recyclerView);
    }


    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
