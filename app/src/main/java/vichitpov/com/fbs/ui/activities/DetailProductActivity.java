package vichitpov.com.fbs.ui.activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;
import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.Convert;
import vichitpov.com.fbs.base.IntentData;
import vichitpov.com.fbs.constant.Url;
import vichitpov.com.fbs.retrofit.response.ProductResponse;

public class DetailProductActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageBack;
    private BannerSlider bannerSlider;
    private TextView textTitle, textDescription, textPrice, textName, textPhone, textEmail, textAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        initView();
        getIntentFromAnotherActivity();

        imageBack.setOnClickListener(this);


    }

    @SuppressLint("SetTextI18n")
    private void getIntentFromAnotherActivity() {
        ProductResponse.Data productResponse = (ProductResponse.Data) getIntent().getSerializableExtra("productList");
        if (productResponse != null) {
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

            if (productResponse.getContactemail().equals("norton@null.com")) {
                textEmail.setVisibility(View.GONE);
            } else {
                textEmail.setVisibility(View.VISIBLE);
                textEmail.setText("Contact Email: " + productResponse.getContactemail());
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
