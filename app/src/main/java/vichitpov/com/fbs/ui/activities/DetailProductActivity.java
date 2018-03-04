package vichitpov.com.fbs.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.Convert;
import vichitpov.com.fbs.base.IntentData;
import vichitpov.com.fbs.constant.Url;

public class DetailProductActivity extends AppCompatActivity implements View.OnClickListener {
    private String id, title, description, priceFrom, priceTo, thumbnail, contactName, contactPhone, contactEmail, contactAddress, date;
    private ImageView imageBack;
    private TextView textTitle, textDescription, textPrice, textName, textPhone, textEmail, textAddress, textDate;
    private FlipperLayout flipperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        initView();
        getIntentFromAnotherActivity();
        setView();

        imageBack.setOnClickListener(this);


    }

    private void setView() {
        if (title != null && description != null && contactPhone != null && contactAddress != null) {
            Log.e("pppp", "title");
            textTitle.setText(title);
            textDescription.setText(description);
            textPhone.setText(contactPhone);
            textAddress.setText(contactAddress);

        }
        if (thumbnail != null) {
            Log.e("pppp", "thumbnail");
            setUpSlider(thumbnail);
        }

        if (priceFrom != null && priceTo != null) {
            Log.e("pppp", Convert.priceConvert(priceFrom, priceTo));
            textPrice.setText(Convert.priceConvert(priceFrom, priceTo));
        }

        if (contactName != null) {
            Log.e("pppp", "contactName");
            textName.setText(contactName);
        } else {
            textName.setVisibility(View.GONE);
        }

        if (contactEmail != null) {
            Log.e("pppp", "contactEmail");
            textEmail.setText(contactEmail);
        } else {
            Log.e("pppp", "else");
            textEmail.setVisibility(View.GONE);
        }

//        if (date != null) {
//            textDate.setText(Convert.dateConverter(Convert.subStringDate(date)));
//        } else {
//            textDate.setVisibility(View.GONE);
//        }


    }

    private void setUpSlider(String image) {

        String url[] = new String[]{image};
        int totalPage = url.length;

        for (int i = 0; i < totalPage; i++) {
            FlipperView view = new FlipperView(getBaseContext());
            final int finalI = i;
            view.setImageUrl(url[i])
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP) //You can use any ScaleType
                    .setOnFlipperClickListener(flipperView -> Toast.makeText(getApplicationContext(), "Clicked: " + finalI, Toast.LENGTH_SHORT).show());

            flipperLayout.setScrollTimeInSec(5);
            flipperLayout.getScrollTimeInSec();
            flipperLayout.addFlipperView(view);
        }
    }

    private void getIntentFromAnotherActivity() {
        id = getIntent().getStringExtra(IntentData.ID);
        title = getIntent().getStringExtra(IntentData.TITLE);
        description = getIntent().getStringExtra(IntentData.DESCRIPTION);
        priceFrom = getIntent().getStringExtra(IntentData.PRICE_FROM);
        priceTo = getIntent().getStringExtra(IntentData.PRICE_TO);
        thumbnail = getIntent().getStringExtra(IntentData.IMAGE_THUMBNAIL);
        contactName = getIntent().getStringExtra(IntentData.CONTACT_NAME);
        contactPhone = getIntent().getStringExtra(IntentData.CONTACT_PHONE);
        contactEmail = getIntent().getStringExtra(IntentData.CONTACT_EMAIL);
        contactAddress = getIntent().getStringExtra(IntentData.CONTACT_ADDRESS);
        date = getIntent().getStringExtra(IntentData.DATE);

    }

    private void initView() {

        imageBack = findViewById(R.id.image_back);
        flipperLayout = findViewById(R.id.flipper_layout_image);

        textTitle = findViewById(R.id.textTitle);
        textDescription = findViewById(R.id.textDescription);
        textPrice = findViewById(R.id.textPrice);
        textName = findViewById(R.id.textName);
        textPhone = findViewById(R.id.textPhone);
        textEmail = findViewById(R.id.textEmail);
        textAddress = findViewById(R.id.textAddress);
        textDate = findViewById(R.id.textDate);


    }


    @Override
    public void onClick(View view) {
        finish();
    }
}
