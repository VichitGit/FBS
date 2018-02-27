package vichitpov.com.fbs.base;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.ui.activity.DetailProductActivity;

/**
 * Created by VichitPov on 2/27/18.
 */

public class IntentData {

    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PRICE_FROM = "PRICE_FROM";
    public static final String PRICE_TO = "PRICE_TO";
    public static final String IMAGE_THUMBNAIL = "IMAGE_THUMBNAIL";
    public static final String CONTACT_NAME = "CONTACT_NAME";
    public static final String CONTACT_PHONE = "CONTACT_PHONE";
    public static final String CONTACT_EMAIL = "CONTACT_EMAIL";
    public static final String CONTACT_ADDRESS = "CONTACT_ADDRESS";
    public static final String DATE = "DATE";


    public static void sendData(Context context, List<ProductResponse.Data> productList, int position) {
        Intent intent = new Intent(context, DetailProductActivity.class);
        intent.putExtra(ID, productList.get(position).getId());
        intent.putExtra(TITLE, productList.get(position).getTitle());
        intent.putExtra(DESCRIPTION, productList.get(position).getDescription());
        intent.putExtra(PRICE_FROM, productList.get(position).getPrice().get(0).getMin());
        intent.putExtra(PRICE_TO, productList.get(position).getPrice().get(0).getMax());
        intent.putExtra(IMAGE_THUMBNAIL, productList.get(position).getProductimages().get(0));
        intent.putExtra(CONTACT_NAME, productList.get(position).getContactname());
        intent.putExtra(CONTACT_PHONE, productList.get(position).getContactphone());
        intent.putExtra(CONTACT_EMAIL, productList.get(position).getContactemail());
        intent.putExtra(CONTACT_ADDRESS, productList.get(position).getContactaddress());
        intent.putExtra(DATE, productList.get(position).getContactname());
        context.startActivity(intent);
    }
}
