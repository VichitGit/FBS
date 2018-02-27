package vichitpov.com.fbs.retrofit.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vichitpov.com.fbs.retrofit.response.ProductResponse;

/**
 * Created by VichitDeveloper on 2/25/18.
 */

public interface ApiService {

    @GET("buys")
    Call<ProductResponse> singlePageBuyer();

    @GET("sells")
    Call<ProductResponse> singlePageSeller();

    @GET("buys")
    Call<ProductResponse> seeMoreBuyerLoadByPagination(@Query("page") int page);

    @GET("sells")
    Call<ProductResponse> seeMoreSellerLoadByPagination(@Query("page") int page);

}
