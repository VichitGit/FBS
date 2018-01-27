package vichitpov.com.fbs.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;
import vichitpov.com.fbs.R;

public class DetailProductSellerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_seller);

        FlipperLayout flipperLayout = findViewById(R.id.flipper_layout_image);
        int num_of_pages = 3;

        String url[] = new String[]{
                "https://i.pinimg.com/736x/ec/9d/95/ec9d95f5eb8c1d02597bba17e00a4be3--ducati-superbike-moto-ducati.jpg",
                "https://www.classifiedmoto.com/wp-content/uploads/2017/07/frank-homepage.jpg",
                "https://cdn.yamaha-motor.eu/product_assets/2016/MT07MC/950-75/2016-Yamaha-MT-07-Moto-Cage-EU-Night-Fluo-Studio-001.jpg"
        };

        for (int i = 0; i < num_of_pages; i++) {
            FlipperView view = new FlipperView(getBaseContext());
            final int finalI = i;
            view.setImageUrl(url[i])
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP) //You can use any ScaleType
                    .setDescription("Description")
                    .setOnFlipperClickListener(new FlipperView.OnFlipperClickListener() {
                        @Override
                        public void onFlipperClick(FlipperView flipperView) {
                            Toast.makeText(getApplicationContext(), "Clicked: " + finalI, Toast.LENGTH_SHORT).show();
                        }
                    });

            flipperLayout.setScrollTimeInSec(3);
            flipperLayout.getScrollTimeInSec();
            flipperLayout.addFlipperView(view);
        }
    }

}
