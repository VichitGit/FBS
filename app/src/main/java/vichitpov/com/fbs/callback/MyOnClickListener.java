package vichitpov.com.fbs.callback;

import android.view.View;

import java.util.List;

import vichitpov.com.fbs.retrofit.response.ProductResponse;

/**
 * Created by Vichit on 2/27/18.
 */

public interface MyOnClickListener {


    void setOnItemClick(int position, ProductResponse.Data productResponse);

    void setOnViewClick(int position, int id, View view);


}
