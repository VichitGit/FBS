package vichitpov.com.fbs.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.retrofit.response.FavoriteResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;

/**
 * Created by VichitPov on 3/12/18.
 */

public class Retrofit {

    public static void addFavorite(Context context, String accessToken, int id) {
        SpotsDialog dialog = new SpotsDialog(context, "Adding favorite!");
        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        Call<FavoriteResponse> call = apiService.addFavorite(accessToken, id);
        dialog.show();
        call.enqueue(new Callback<FavoriteResponse>() {
            @Override
            public void onResponse(@NonNull Call<FavoriteResponse> call, @NonNull Response<FavoriteResponse> response) {
                if (response.isSuccessful()) {
                    Log.e("pppp success", response.body().getData().toString());
                    Toast.makeText(context, "Added favorite", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    Toast.makeText(context, "Error connection. please try again", Toast.LENGTH_SHORT).show();
                    Log.e("pppp else", response.code() + " = " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<FavoriteResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
                Toast.makeText(context, "Server problem!", Toast.LENGTH_SHORT).show();
                Log.e("pppp", "onFailure: " + t.getMessage());
            }
        });
    }

    public static void removeFavorite(Context context, String accessToken, int id) {
        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        SpotsDialog dialog = new SpotsDialog(context, "Removing favorite!");
        dialog.show();
        Call<String> call = apiService.removeFavorite(accessToken, id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 204) {
                        dialog.dismiss();
                        Toast.makeText(context, "Removed favorite", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(context, "Error connection. please try again", Toast.LENGTH_SHORT).show();
                    Log.e("pppp", "Remove Else: " + response.code() + " = " + response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
                Toast.makeText(context, "Server problem!", Toast.LENGTH_SHORT).show();
                Log.e("pppp", "onFailure: " + t.getMessage());

            }
        });
    }

    public static void countView(String accessToken, int id) {
        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        Call<JSONObject> call = apiService.countView(accessToken, id);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(@NonNull Call<JSONObject> call, @NonNull Response<JSONObject> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Log.e("pppp", "countViewSuccess: " + response.body().toString());
                } else {
                    Log.e("pppp", "else: " + response.code() + " = " + response.message());
                }


            }

            @Override
            public void onFailure(@NonNull Call<JSONObject> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("pppp", "onFailure: " + t.getMessage());
            }
        });
    }

    public static void deleteUserPost(Context context, String accessToken, int id) {
        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        SpotsDialog dialog = new SpotsDialog(context, "Deleting...");
        dialog.show();
        Call<String> call = apiService.deleteUserPost(accessToken, id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 204) {
                        dialog.dismiss();
                        Toast.makeText(context, "Delete successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                    Log.e("pppp", "else: " + response.code() + " = " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
                Log.e("pppp", "onFailure: " + t.getMessage());
            }
        });


    }

    public static MultipartBody.Part multipartBoy(File file) {
        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData("image_file[]", file.getName(), requestBodyImage);
    }

    public static MultipartBody.Part returnNull() {
        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        return MultipartBody.Part.createFormData("image_file[]", "", requestBodyImage);
    }
}
