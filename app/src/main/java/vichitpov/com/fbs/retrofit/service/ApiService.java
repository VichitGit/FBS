package vichitpov.com.fbs.retrofit.service;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vichitpov.com.fbs.retrofit.response.CategoriesResponse;
import vichitpov.com.fbs.retrofit.response.FavoriteResponse;
import vichitpov.com.fbs.retrofit.response.ImagePostResponse;
import vichitpov.com.fbs.retrofit.response.ProductPostedResponse;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.response.UserInformationResponse;

/**
 * Created by VichitPov on 2/25/18.
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

    @GET("user")
    Call<UserInformationResponse> getUserInformation(@Header("access-token") String accessToken);

    @GET("user/sells")
    Call<ProductResponse> getProductSellSpecificUser(@Header("access-token") String accessToken, @Query("page") int page);

    @GET("user/buys")
    Call<ProductResponse> getProductBuySpecificUser(@Header("access-token") String accessToken, @Query("page") int page);

    @GET("categoies/parents")
    Call<CategoriesResponse> getAllCategories();

    @GET("sells/category/{category_id}")
    Call<ProductResponse> getProductByCategory(@Path("category_id") int categoryId, @Query("page") int page);

    @GET("user/favorites/")
    Call<ProductResponse> getAllUserFavorite(@Header("access-token") String accessToken, @Query("page") int page);

    @GET("search")
    Call<ProductResponse> searchAll(@Query("s") String keyword, @Query("page") int page);

    @GET("view/{id}")
    Call<JSONObject> countView(@Header("access-Token") String accessToken, @Path("id") int productId);

    @GET("user/favorites/{id}")
    Call<FavoriteResponse> addFavorite(@Header("access-token") String accessToken, @Path("id") int productId);

    @GET("{type}/topview")
    Call<ProductResponse> topSellList(@Path("type") String type);

    @GET("user/post/active/{id}")
    Call<ProductPostedResponse> activeProductExpried(@Header("access-token") String accessToken, @Path("id") int id);

    @GET("user/post/expire")
    Call<ProductResponse> getAllExpiredProduct(@Header("access-token") String accessToken);


    @PUT("user")
    @FormUrlEncoded
    Call<UserInformationResponse> updateUser(@Header("access-token") String accessToken, @Field("first_name") String firstName,
                                             @Field("last_name") String lastName, @Field("gender") String gender,
                                             @Field("address") String address, @Field("city") String city, @Field("description") String description);

    @PUT("user")
    @FormUrlEncoded
    Call<UserInformationResponse> updateOnlyDescription(@Header("access-token") String accessToken, @Field("description") String description);


    @DELETE("user/post/{id}")
    Call<String> deleteUserPost(@Header("access-token") String accessToken, @Path("id") int productId);

    @DELETE("user/favorites/{id}")
    Call<String> removeFavorite(@Header("access-token") String accessToken, @Path("id") int productId);

    @DELETE("images/post/{imageUrl}")
    Call<String> removeImageAfterUpdate(@Header("access-token") String accessToken, @Path("imageUrl") String ImageUrl);

    @POST("user/post")
    @FormUrlEncoded
    Call<ProductPostedResponse> postProduct(@Header("access-token") String accessToken,
                                            @Field("post_type") String postType,
                                            @Field("title") String title,
                                            @Field("category_id") int category,
                                            @Field("description") String description,
                                            @Field("price_from") int priceFrom,
                                            @Field("price_to") int priceTo,
                                            @Field("contact_name") String contactName,
                                            @Field("contact_phone") String contactPhone,
                                            @Field("contact_email") String contactEmail,
                                            @Field("product_image") String image,
                                            @Field("contact_address") String contactAddress,
                                            @Field("contact_address_map_coordinate") String addressMap);


    @Multipart
    @POST("images/profile")
    Call<UserInformationResponse> updateUserImageProfile(@Header("access-token") String accessToken,
                                                         @Part MultipartBody.Part imageProfile);

    @Multipart
    @POST("images/post")
    Call<ImagePostResponse> uploadImage(@Header("access-token") String accessToken,
                                        @Part MultipartBody.Part image1,
                                        @Part MultipartBody.Part image2,
                                        @Part MultipartBody.Part image3,
                                        @Part MultipartBody.Part image4,
                                        @Part MultipartBody.Part image5);

    @Multipart
    @POST("images/post")
    Call<ImagePostResponse> uploadMultipartImage(@Header("access-token") String accessToken,
                                                 @Part MultipartBody.Part[] filesImage);

    @FormUrlEncoded
    @PUT("user/post/{product_id}")
    Call<ProductPostedResponse> updateProduct(@Path("product_id") int id,
                                              @Header("access-token") String accessToken,
                                              @Field("title") String title,
                                              @Field("description") String description,
                                              @Field("price_from") double priceStart,
                                              @Field("price_to") double priceEnd,
                                              @Field("post_type") String postType,
                                              @Field("category_id") int categoryId,
                                              @Field("product_image") String image,
                                              @Field("contact_name") String contactName,
                                              @Field("contact_phone") String contactPhone,
                                              @Field("contact_email") String contactEmail,
                                              @Field("contact_address") String contactAddress,
                                              @Field("contact_address_map_coordinate") String mapAddress);


}

