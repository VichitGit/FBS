package vichitpov.com.fbs.ui.activities.post;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.BaseAppCompatActivity;
import vichitpov.com.fbs.base.InternetConnection;
import vichitpov.com.fbs.base.VailidationEmail;
import vichitpov.com.fbs.constant.AnyConstant;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ProductPostedResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activities.ChooseCategoryActivity;
import vichitpov.com.fbs.ui.activities.login.StartLoginActivity;

import static vichitpov.com.fbs.constant.AnyConstant.POST_TO_BUY_RESULT_CODE;

public class PostToBuyActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private UserInformationManager userInformationManager;
    private EditText editName, editEmail, editAddress, editPhone, editTitle, editPriceFrom, editPriceTo, editDescription;
    private TextView validationTitle, validationCategory, validationPriceStart, validationPriceTo, validationDescription, validationName, validationPhone, validationAddress, validationEmail;
    private TextView textCategory;
    private LinearLayout layoutValidationPrice;
    private int selectedCategory = 1010;
    private Button buttonUpload;
    private SpotsDialog dialog;
    private String coordinate;
    private ImageView imageBack;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_to_buy);

        initView();
        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        dialog = new SpotsDialog(this, getString(R.string.alert_dialog_uploading));

        clearValidation();
        displayValidationProduct(false);
        displayValidationContact(false);

        checkUserInformation();
        listener();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("pppp", "onActivityResult");
        if (requestCode == POST_TO_BUY_RESULT_CODE && resultCode != RESULT_CANCELED) {

            selectedCategory = Integer.parseInt(data.getStringExtra(AnyConstant.CATEGORY_ID));
            textCategory.setText(data.getStringExtra(AnyConstant.CATEGORY_NAME));

        }
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
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.textCategory) {
            Intent intent = new Intent(getApplicationContext(), ChooseCategoryActivity.class);
            intent.putExtra(AnyConstant.POST_TO_BUY_TEXT, AnyConstant.POST_TO_BUY_RESULT_CODE);
            startActivityForResult(intent, POST_TO_BUY_RESULT_CODE);
        } else if (id == R.id.imageBack) {
            finish();
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

        if (title.isEmpty() && selectedCategory == 1010 && priceFrom.isEmpty() && priceTo.isEmpty() && description.isEmpty()) {
            displayValidationProduct(true);

            validationTitle.setText(R.string.validation_title);
            validationCategory.setText(R.string.validation_category);
            validationPriceStart.setText(R.string.validation_price);
            validationPriceTo.setText(R.string.validation_price);
            validationDescription.setText(R.string.validation_description);
        } else {
            if (title.isEmpty()) {
                validationTitle.setVisibility(View.VISIBLE);
                validationTitle.setText(getString(R.string.validation_title));

            } else if (selectedCategory == 1010) {
                validationCategory.setVisibility(View.VISIBLE);
                validationCategory.setText(getString(R.string.validation_category));

            } else if (priceFrom.isEmpty()) {
                layoutValidationPrice.setVisibility(View.VISIBLE);
                validationPriceStart.setText(getString(R.string.validation_price));

            } else if (priceTo.isEmpty()) {
                layoutValidationPrice.setVisibility(View.VISIBLE);
                validationPriceTo.setText(getString(R.string.validation_price));

            } else if (description.isEmpty()) {

                validationDescription.setVisibility(View.VISIBLE);
                validationDescription.setText(getString(R.string.validation_description));

            } else if (phone.isEmpty()) {
                validationPhone.setVisibility(View.VISIBLE);
                validationPhone.setText(R.string.validation_contact_phone);

            } else if (name.isEmpty()) {
                validationName.setVisibility(View.VISIBLE);
                validationName.setText(R.string.validation_contact_name);

            } else if (address.isEmpty()) {
                validationAddress.setVisibility(View.VISIBLE);
                validationAddress.setText(R.string.validation_address);

            } else if (!VailidationEmail.isEmailValid(email) && !email.isEmpty()) {
                validationEmail.setVisibility(View.VISIBLE);
                validationEmail.setText("Invalid email(example@gmail.com");
            } else {
                if (InternetConnection.isNetworkConnected(this)) {
                    int category = selectedCategory;
                    int priceStart = Integer.parseInt(priceFrom);
                    int priceEnd = Integer.parseInt(priceTo);

                    if (email.isEmpty()) {
                        email = "norton@null.com";
                    }
                    postToBuy(title, category, description, priceStart, priceEnd, name, phone, email, address, coordinate);

                } else {
                    Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void postToBuy(String title, int category, String description, int priceFrom, int priceTo, String contactName, String contactPhone, String contactEmail, String address, String mapCondinator) {
        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        String accessToken = userInformationManager.getUser().getAccessToken();

        dialog.show();
        Call<ProductPostedResponse> call = apiService.postProduct(accessToken, "buy", title, category, description, priceFrom, priceTo, contactName, contactPhone, contactEmail, "", address, mapCondinator);
        call.enqueue(new Callback<ProductPostedResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductPostedResponse> call, @NonNull Response<ProductPostedResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PostToBuyActivity.this, R.string.text_upload_success, Toast.LENGTH_LONG).show();
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
        coordinate = userInformationManager.getUser().getMapCondinate();

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

    private void listener() {
        buttonUpload.setOnClickListener(this);
        textCategory.setOnClickListener(this);
        imageBack.setOnClickListener(this);
    }

    private void initView() {

        imageBack = findViewById(R.id.imageBack);
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
        textCategory = findViewById(R.id.textCategory);

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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
