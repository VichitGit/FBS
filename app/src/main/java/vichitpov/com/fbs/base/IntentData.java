package vichitpov.com.fbs.base;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.ui.activities.DetailProductActivity;
import vichitpov.com.fbs.ui.activities.EditProductActivity;

/**
 * Created by VichitPov on 2/27/18.
 */

public class IntentData {
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String PHONE = "PHONE";
    public static final String SEND_FROM_MAIN_ACTIVITY = "SEND_FROM_MAIN_ACTIVITY";


    public static void sendProduct(Context context, ProductResponse.Data product) {
        Intent intent = new Intent(context, EditProductActivity.class);
        intent.putExtra("ProductList", product);
        context.startActivity(intent);
    }
}
