package vichitpov.com.fbs.callback;

import vichitpov.com.fbs.retrofit.response.ProductResponse;

/**
 * Created by VichitPov on 3/20/18.
 */

public interface OnClickEdit {

    void setOnClickEdit(int position, ProductResponse.Data productResponse);

}
