package vichitpov.com.fbs.ui.activities.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.BaseAppCompatActivity;
import vichitpov.com.fbs.base.InternetConnection;
import vichitpov.com.fbs.constant.AnyConstant;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.UserInformationResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activities.MainActivity;


public class RegisterUserActivity extends BaseAppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText editFirstName, editLastName, editAddressStress, editAddressCommune, editAddressDistricts, editAddressCity;
    private MaterialSpinner spinnerGender;
    private Button buttonSave;
    private UserInformationManager userInformationManager;
    private String userAccessToken, selectedGender = "null";
    private LinearLayout layoutValidationName, layoutValidationAddress1, layoutValidationAddress2;
    private TextView validationFirstName, validationLastName, validationGender, validationStress, validationCommune, validationDistricts, validationCity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        init();

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        userAccessToken = getIntent().getStringExtra(AnyConstant.ACCESS_TOKEN);

        setUpSpinnerGender();
        listener();


    }


    //spinner event selection
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            selectedGender = "Male";
        } else if (i == 1) {
            selectedGender = "Female";
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        selectedGender = "null";

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

        hideLayout(false);
        clearTextValidation();

        int id = view.getId();
        if (id == R.id.buttonOk) {
            String mFirstName = editFirstName.getText().toString();
            String mLastName = editLastName.getText().toString();
            String mGender = selectedGender;
            String addressStreet = editAddressStress.getText().toString();
            String addressDistricts = editAddressDistricts.getText().toString();
            String addressCommune = editAddressCommune.getText().toString();
            String addressCity = editAddressCity.getText().toString();

            if (mFirstName.isEmpty() && mLastName.isEmpty() && mGender.equals("null") &&
                    addressStreet.isEmpty() && addressDistricts.isEmpty() && addressCommune.isEmpty() && addressCity.isEmpty()) {

                hideLayout(true);

                validationFirstName.setText(R.string.validation_first_name);
                validationLastName.setText(R.string.validation_last_name);
                validationGender.setText(R.string.validation_gender);
                validationStress.setText(R.string.validation_street);
                validationDistricts.setText(R.string.validation_districts);
                validationCommune.setText(R.string.validation_commune);
                validationCity.setText(R.string.validation_city);

            } else {
                if (mFirstName.isEmpty()) {
                    layoutValidationName.setVisibility(View.VISIBLE);
                    validationFirstName.setText(R.string.validation_first_name);
                    editFirstName.setFocusable(true);

                } else if (mLastName.isEmpty()) {
                    layoutValidationName.setVisibility(View.VISIBLE);
                    validationLastName.setText(R.string.validation_last_name);
                    editLastName.setFocusable(true);

                } else if (mGender.equals("null")) {
                    validationGender.setVisibility(View.VISIBLE);
                    validationGender.setText(R.string.validation_gender);
                    validationGender.setFocusable(true);

                } else if (addressStreet.isEmpty()) {
                    layoutValidationAddress1.setVisibility(View.VISIBLE);
                    validationStress.setText(R.string.validation_street);
                    editAddressStress.setFocusable(true);

                } else if (addressCommune.isEmpty()) {
                    layoutValidationAddress1.setVisibility(View.VISIBLE);
                    validationCommune.setText(R.string.validation_districts);
                    editAddressCommune.setFocusable(true);

                } else if (addressDistricts.isEmpty()) {
                    layoutValidationAddress2.setVisibility(View.VISIBLE);
                    validationCommune.setText(R.string.validation_commune);
                    editAddressDistricts.setFocusable(true);

                } else if (addressCity.isEmpty()) {
                    layoutValidationAddress2.setVisibility(View.VISIBLE);
                    validationCity.setText(R.string.validation_city);
                    editAddressCity.setFocusable(true);
                } else {
                    String mergeAddress = addressStreet + ", " + addressCommune + ", " + addressDistricts;
                    if (InternetConnection.isNetworkConnected(getApplicationContext())) {
                        uploadInformation(userAccessToken, mFirstName, mLastName, mGender, mergeAddress, addressCity);
                    } else {
                        Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void uploadInformation(String accessToken, String firstName, String lastName, String gender, String address, String city) {
        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        Call<UserInformationResponse> call = apiService.updateUser(accessToken, firstName, lastName, gender, address, city, "Introduction yourself", "11.562108, 104.888535");
        call.enqueue(new Callback<UserInformationResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserInformationResponse> call, @NonNull Response<UserInformationResponse> response) {
                if (response.isSuccessful()) {

                    userInformationManager.saveInformation(response.body());
                    userInformationManager.saveAccessToken(accessToken);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();

                } else if (response.code() == 401) {
                    Toast.makeText(RegisterUserActivity.this, R.string.account_unavailable, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), StartLoginActivity.class));

                } else {
                    Toast.makeText(RegisterUserActivity.this, R.string.server_problem, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<UserInformationResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("pppp", t.getMessage());
                Toast.makeText(RegisterUserActivity.this, R.string.failed_connection, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setUpSpinnerGender() {

        String[] ITEMS = {"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

    }


    private void clearTextValidation() {

        validationFirstName.setText("");
        validationLastName.setText("");
        validationGender.setText("");
        validationStress.setText("");
        validationDistricts.setText("");
        validationCommune.setText("");
        validationCity.setText("");

    }

    private void hideLayout(boolean visible) {
        if (visible) {
            layoutValidationName.setVisibility(View.VISIBLE);
            validationGender.setVisibility(View.VISIBLE);
            layoutValidationAddress1.setVisibility(View.VISIBLE);
            layoutValidationAddress2.setVisibility(View.VISIBLE);

        } else {
            layoutValidationName.setVisibility(View.GONE);
            validationGender.setVisibility(View.GONE);
            layoutValidationAddress1.setVisibility(View.GONE);
            layoutValidationAddress2.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void listener() {

        spinnerGender.setOnItemSelectedListener(this);
        buttonSave.setOnClickListener(this);

    }

    private void init() {

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editAddressStress = findViewById(R.id.editAddress1);
        editAddressCommune = findViewById(R.id.editAddress2);
        editAddressDistricts = findViewById(R.id.editAddress3);
        editAddressCity = findViewById(R.id.editAddress4);
        spinnerGender = findViewById(R.id.spinner);

        buttonSave = findViewById(R.id.buttonOk);

        layoutValidationAddress1 = findViewById(R.id.layoutValidationAddress1);
        layoutValidationAddress2 = findViewById(R.id.layoutValidationAddress2);
        layoutValidationName = findViewById(R.id.layoutValidationName);

        validationFirstName = findViewById(R.id.textValidationFirstName);
        validationLastName = findViewById(R.id.textValidationLastName);
        validationGender = findViewById(R.id.textValidationGender);
        validationStress = findViewById(R.id.textValidationAddressStreet);
        validationCommune = findViewById(R.id.textValidationAddressCommune);
        validationDistricts = findViewById(R.id.textValidationAddressDistrict);
        validationCity = findViewById(R.id.textValidationAddressCity);

        hideLayout(false);
        clearTextValidation();


    }
}
