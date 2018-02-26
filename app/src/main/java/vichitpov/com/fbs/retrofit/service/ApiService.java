package vichitpov.com.fbs.retrofit.service;

import retrofit2.Call;
import retrofit2.http.GET;
import vichitpov.com.fbs.retrofit.response.ProductResponse;

/**
 * Created by VichitDeveloper on 2/25/18.
 */

public interface ApiService {

    @GET("buys")
    Call<ProductResponse> singlePageBuyer();

}
