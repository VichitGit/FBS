package vichitpov.com.fbs.ui.activity.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.ui.activity.SettingsActivity;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ImageView imageBack = findViewById(R.id.image_back);
        ImageView imageSetting = findViewById(R.id.image_setting);
        LinearLayout linearEditProfile = findViewById(R.id.linear_edit_profile);
        LinearLayout linearSold = findViewById(R.id.linear_sold);
        LinearLayout linearBought = findViewById(R.id.linear_bought);
        LinearLayout linearRePost = findViewById(R.id.linear_reload);


        imageBack.setOnClickListener(this);
        imageSetting.setOnClickListener(this);
        linearEditProfile.setOnClickListener(this);
        linearSold.setOnClickListener(this);
        linearBought.setOnClickListener(this);
        linearRePost.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.image_back)
            finish();
        else if (id == R.id.linear_edit_profile)
            startActivity(new Intent(getApplicationContext(), EditUserProfileActivity.class));
        else if (id == R.id.image_setting)
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        else if (id == R.id.linear_sold)
            startActivity(new Intent(getApplicationContext(), ProductSoldActivity.class));
        else if (id == R.id.linear_bought)
            startActivity(new Intent(getApplicationContext(), ProductBoughtActivity.class));
        else if (id == R.id.linear_reload)
            dialogBottom();


    }


    private void dialogBottom() {
        BottomSheetMenuDialog dialog = new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setIconTintColorResource(R.color.colorHint)
                .setMenu(R.menu.menu_dialog_bottom_sheet)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.dialog_bottom_re_post_buy) {
                            startActivity(new Intent(getApplicationContext(), RePostToBuyActivity.class));
                        } else if (item.getItemId() == R.id.dialog_bottom_re_post_sell) {
                            startActivity(new Intent(getApplicationContext(), RePostToSellActivity.class));
                        }

                    }
                })
                .createDialog();

        dialog.show();
    }
}



























