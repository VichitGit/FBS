package vichitpov.com.fbs.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.ui.activities.post.PostToBuyActivity;
import vichitpov.com.fbs.ui.activities.post.PostToSellActivity;


/**
 * Created by VichitDeveloper on 3/25/18.
 */

public class DiologBottom {

    //dialog bottom
    public static void dialogBottom(Context context) {
        @SuppressLint("ResourceAsColor") BottomSheetMenuDialog dialog = new BottomSheetBuilder(context, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setIconTintColorResource(R.color.colorPrimary)
                .setItemTextColor(R.color.colorPrimary)
                .setMenu(R.menu.menu_dialog_post)
                .setItemClickListener(item -> {
                    if (item.getItemId() == R.id.dialog_bottom_post_to_buy) {
                        context.startActivity(new Intent(context, PostToBuyActivity.class));
                    } else if (item.getItemId() == R.id.dialog_bottom_post_to_sell) {
                        context.startActivity(new Intent(context, PostToSellActivity.class));
                    }

                })
                .createDialog();
        dialog.show();
    }

}
