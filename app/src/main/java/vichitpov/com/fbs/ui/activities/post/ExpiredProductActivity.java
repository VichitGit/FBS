package vichitpov.com.fbs.ui.activities.post;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.StatusProductAdapter;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;

public class ExpiredProductActivity extends AppCompatActivity {
    private ImageView imageBack;
    private TextView textNotFound;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private UserInformationManager userInformationManager;
    private StatusProductAdapter adapter;
    private ApiService apiService;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expired_product);

        initView();
        initRecyclerView();

        apiService = ServiceGenerator.createService(ApiService.class);
        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        accessToken = userInformationManager.getUser().getAccessToken();

        requestExpiredProduct();

    }

    //get all user's product expired
    private void requestExpiredProduct() {
        if (returnToken()) {
            progressBar.setVisibility(View.VISIBLE);
            textNotFound.setVisibility(View.GONE);

            Call<ProductResponse> call = apiService.getAllExpiredProduct(accessToken);
            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getData().size() != 0) {
                            adapter.addItem(response.body().getData());
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setAdapter(adapter);

                        } else {
                            progressBar.setVisibility(View.GONE);
                            textNotFound.setVisibility(View.VISIBLE);

                        }
                    } else {
                        Log.e("pppp", "else: " + response.code() + " = " + response.message());
                        progressBar.setVisibility(View.GONE);
                        textNotFound.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    textNotFound.setVisibility(View.VISIBLE);
                    t.printStackTrace();
                    Log.e("pppp", "onFailure: " + t.getMessage());
                }
            });

        }
    }

    //check token return
    private boolean returnToken() {
        if (!accessToken.equals("N/A")) {
            return true;
        } else {
            return false;
        }
    }

    //setup reference adapter
    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StatusProductAdapter(this);

    }

    private void initView() {
        textNotFound = findViewById(R.id.textNotFound);
        imageBack = findViewById(R.id.imageBack);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        progressBar.setVisibility(View.GONE);
        textNotFound.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
