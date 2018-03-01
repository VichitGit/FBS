package vichitpov.com.fbs.ui.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.InternetConnection;
import vichitpov.com.fbs.base.VailidationEmail;
import vichitpov.com.fbs.preferece.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ProductPostedResponse;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activity.login.StartLoginActivity;

public class PostToBuyActivity extends AppCompatActivity implements View.OnClickListener {

    private UserInformationManager userInformationManager;
    private EditText editName, editEmail, editAddress, editPhone, editTitle, editPriceFrom, editPriceTo, editDescription;
    private TextView validationTitle, validationCategory, validationPriceStart, validationPriceTo, validationDescription, validationName, validationPhone, validationAddress, validationEmail;
    private LinearLayout layoutValidationPrice;
    private String selectedCategory = "null";
    private Button buttonUpload;
    private SpotsDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_to_buy);
        initView();
        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        dialog = new SpotsDialog(this, "Uploading...");

        clearValidation();
        displayValidationProduct(false);
        displayValidationContact(false);

        checkUserInformation();
        setUpCategory();
        listener();


    }

    private void listener() {
        buttonUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonUpload) {
            clearValidation();
            displayValidationProduct(false);
            displayValidationContact(false);
            if (InternetConnection.isNetworkConnected(this)) {
                checkValidation();
            } else {
                Toast.makeText(this, "No Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void displayValidationContact(boolean isDisplay) {
        if (isDisplay) {
            validationName.setVisibility(View.VISIBLE);
            validationPhone.setVisibility(View.VISIBLE);
            validationAddress.setVisibility(View.VISIBLE);
            validationEmail.setVisibility(View.VISIBLE);
        } else {
            validationName.setVisibility(View.GONE);
            validationPhone.setVisibility(View.GONE);
            validationAddress.setVisibility(View.GONE);
            validationEmail.setVisibility(View.GONE);
        }
    }

    private void displayValidationProduct(boolean isDisplay) {
        if (isDisplay) {
            layoutValidationPrice.setVisibility(View.VISIBLE);
            validationTitle.setVisibility(View.VISIBLE);
            validationCategory.setVisibility(View.VISIBLE);
            validationDescription.setVisibility(View.VISIBLE);

        } else {
            layoutValidationPrice.setVisibility(View.GONE);
            validationTitle.setVisibility(View.GONE);
            validationCategory.setVisibility(View.GONE);
            validationDescription.setVisibility(View.GONE);

        }
    }

    private void clearValidation() {
        validationTitle.setText("");
        validationCategory.setText("");
        validationPriceStart.setText("");
        validationPriceTo.setText("");
        validationDescription.setText("");
        validationName.setText("");
        validationPhone.setText("");
        validationAddress.setText("");
        validationEmail.setText("");

    }

    private void setUpCategory() {

        List<String> list = new ArrayList<>();
        list.add("Phone");
        list.add("Computer");
        list.add("Car");
        list.add("Shirt");
        list.add("Electronic");

        MaterialSpinner spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerCategory.setItems(list);
        spinnerCategory.setOnItemSelectedListener((view, position, id, item) -> {
            if (position == 0) {
                selectedCategory = "null";
            } else {
                selectedCategory = "69";
            }
            Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
        });
    }

    @SuppressLint("SetTextI18n")
    private void checkValidation() {
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String address = editAddress.getText().toString();
        String phone = editPhone.getText().toString();
        String title = editTitle.getText().toString();
        String priceFrom = editPriceFrom.getText().toString();
        String priceTo = editPriceTo.getText().toString();
        String description = editDescription.getText().toString();

        if (title.isEmpty() && selectedCategory.equals("null") && priceFrom.isEmpty() && priceTo.isEmpty() && description.isEmpty()) {
            displayValidationProduct(true);

            validationTitle.setText("Required title");
            validationCategory.setText("Please select category");
            validationPriceStart.setText("Required price start");
            validationPriceTo.setText("Required price to");
            validationDescription.setText("Required description");
        } else {
            if (title.isEmpty()) {
                validationTitle.setVisibility(View.VISIBLE);
                validationTitle.setText("Required title");

            } else if (selectedCategory.equals("null")) {
                validationCategory.setVisibility(View.VISIBLE);
                validationCategory.setText("Please select category");

            } else if (priceFrom.isEmpty()) {
                layoutValidationPrice.setVisibility(View.VISIBLE);
                validationPriceStart.setText("Required price start");

            } else if (priceTo.isEmpty()) {
                layoutValidationPrice.setVisibility(View.VISIBLE);
                validationPriceTo.setText("Required price to");

            } else if (description.isEmpty()) {

                validationDescription.setVisibility(View.VISIBLE);
                validationDescription.setText("Required description");

            } else if (phone.isEmpty()) {
                validationPhone.setVisibility(View.VISIBLE);
                validationPhone.setText("Required contact phone");

            } else if (name.isEmpty()) {
                validationName.setVisibility(View.VISIBLE);
                validationName.setText("Required contact name");

            } else if (address.isEmpty()) {
                validationAddress.setVisibility(View.VISIBLE);
                validationAddress.setText("Required contact address");

            } else if (!VailidationEmail.isEmailValid(email) && !email.isEmpty()) {
                validationEmail.setVisibility(View.VISIBLE);
                validationEmail.setText("Invalid email(example@gmail.com");
            } else {
                if (InternetConnection.isNetworkConnected(this)) {
                    int category = Integer.parseInt(selectedCategory);
                    int priceStart = Integer.parseInt(priceFrom);
                    int priceEnd = Integer.parseInt(priceTo);

                    if (email.isEmpty()) {
                        email = "norton@null.com";
                    }
                    postToBuy(title, category, description, priceStart, priceEnd, name, phone, email, address, "101202033030.2022928387");

                } else {

                    Toast.makeText(this, "No Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void postToBuy(String title, int category, String description, int priceFrom, int priceTo, String contactName, String contactPhone, String contactEmail, String address, String mapCondinator) {
        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        String accessToken = userInformationManager.getUser().getAccessToken();

        dialog.show();
        Call<ProductPostedResponse> call = apiService.postToBuy(accessToken, "buy", title, category, description, priceFrom, priceTo, contactName, contactPhone, contactEmail, address, mapCondinator);
        call.enqueue(new Callback<ProductPostedResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductPostedResponse> call, @NonNull Response<ProductPostedResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PostToBuyActivity.this, "Upload successfully.", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    finish();
                } else if (response.code() == 401) {
                    dialog.dismiss();
                    new AlertDialog.Builder(getApplicationContext())
                            .setCancelable(false)
                            .setTitle("Session Expired!")
                            .setMessage("Sorry your session expired. Please try to login again")
                            .setPositiveButton("OK", (dialogInterface, i) -> startActivity(new Intent(getApplicationContext(), StartLoginActivity.class)))
                            .show();
                } else {
                    dialog.dismiss();
                    Log.e("pppp else", response.code() + " = " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductPostedResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                t.printStackTrace();
                Log.e("pppp onFailure", t.getMessage());
            }
        });

    }

    private void checkUserInformation() {
        String firstName = userInformationManager.getUser().getFirstName();
        String lastName = userInformationManager.getUser().getLastName();
        String phone = userInformationManager.getUser().getPhone();
        String address = userInformationManager.getUser().getAddress();
        String email = userInformationManager.getUser().getEmail();

        if (!firstName.equals("N/A") && !lastName.equals("N/A")) {
            String fullName = firstName + " " + lastName;
            editName.setText(fullName);
        }
        if (!phone.equals("N/A")) {
            editPhone.setText(phone);
        }

        if (!address.equals("N/A")) {
            editAddress.setText(address);
        }

        if (!email.equals("N/A")) {
            editEmail.setText(email);
        }
    }

    private void initView() {
        validationTitle = findViewById(R.id.validationTitle);
        validationCategory = findViewById(R.id.validationCategory);
        validationPriceStart = findViewById(R.id.validationPriceFrom);
        validationPriceTo = findViewById(R.id.validationPriceTo);
        validationDescription = findViewById(R.id.validationDescription);
        validationName = findViewById(R.id.validationName);
        validationEmail = findViewById(R.id.validationEmail);
        validationPhone = findViewById(R.id.validationPhone);
        validationAddress = findViewById(R.id.validationAddress);
        layoutValidationPrice = findViewById(R.id.layoutValidationPrice);

        buttonUpload = findViewById(R.id.buttonUpload);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editAddress = findViewById(R.id.editAddress);
        editEmail = findViewById(R.id.editEmail);
        editTitle = findViewById(R.id.editTitle);
        editPriceFrom = findViewById(R.id.editPriceFrom);
        editPriceTo = findViewById(R.id.editPriceTo);
        editDescription = findViewById(R.id.editDescription);


    }
}
