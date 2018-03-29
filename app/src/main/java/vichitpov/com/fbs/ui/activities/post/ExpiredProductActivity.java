package vichitpov.com.fbs.ui.activities.post;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.StatusProductAdapter;
import vichitpov.com.fbs.base.BaseAppCompatActivity;
import vichitpov.com.fbs.base.InternetConnection;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ProductPostedResponse;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;

public class ExpiredProductActivity extends BaseAppCompatActivity {
    private ImageView imageBack;
    private TextView textNotFound;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private UserInformationManager userInformationManager;
    private StatusProductAdapter adapter;
    private ApiService apiService;
    private String accessToken;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expired_product);
        initView();
        initRecyclerView();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.progress_updating));
        progressDialog.setMessage(getString(R.string.progress_repost));

        apiService = ServiceGenerator.createService(ApiService.class);
        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        accessToken = userInformationManager.getUser().getAccessToken();

        requestExpiredProduct();

        imageBack.setOnClickListener(view -> finish());
        adapter.setOnChangeToActive((id, position) -> {
            if (InternetConnection.isNetworkConnected(this)) {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle(getString(R.string.alert_dialog_active));
                alertDialog.setMessage(getString(R.string.alert_dialog_question_repost));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.button_ok), (dialog, which) -> {
                    activeProduct(id, position);
                    dialog.dismiss();
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.button_no), (dialogInterface, i) -> dialogInterface.dismiss());
                alertDialog.show();

            } else {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //re-post product that expired
    private void activeProduct(int id, int position) {
        progressDialog.show();
        Call<ProductPostedResponse> call = apiService.activeProductExpried(accessToken, id);
        call.enqueue(new Callback<ProductPostedResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductPostedResponse> call, @NonNull Response<ProductPostedResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    adapter.removeActivedItem(position);
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductPostedResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
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
        recyclerView.setAdapter(adapter);

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
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
