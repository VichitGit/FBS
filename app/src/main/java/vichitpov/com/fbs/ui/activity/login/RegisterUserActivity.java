package vichitpov.com.fbs.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.IntentData;
import vichitpov.com.fbs.base.InternetConnection;
import vichitpov.com.fbs.preferece.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.UserInformationResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activity.MainActivity;


public class RegisterUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText editFirstName, editLastName, editPhone, editEmail, editAddressStress, editAddressCommune, editAddressDistricts, editAddressCity;
    private MaterialSpinner spinnerGender;
    private Button buttonSave;
    private UserInformationManager userInformationManager;
    private String userAccessToken, userPhone, selectedGender = "null";
    private LinearLayout layoutValidationName, layoutValidationAddress1, layoutValidationAddress2;
    private TextView validationFirstName, validationLastName, validationGender, validationPhone, validationEmail, validationStress, validationCommune, validationDistricts, validationCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));

        init();
        getDataFromIntent();
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
            String mPhone = editPhone.getText().toString();
            String mEmail = editEmail.getText().toString();
            String addressStreet = editAddressStress.getText().toString();
            String addressDistricts = editAddressDistricts.getText().toString();
            String addressCommune = editAddressCommune.getText().toString();
            String addressCity = editAddressCity.getText().toString();

            if (mFirstName.isEmpty() && mLastName.isEmpty() && mGender.equals("null") && mPhone.isEmpty() && mEmail.isEmpty()
                    && addressStreet.isEmpty() && addressDistricts.isEmpty() && addressCommune.isEmpty() && addressCity.isEmpty()) {

                hideLayout(true);

                validationFirstName.setText("Required first name");
                validationLastName.setText("Required last name");
                validationPhone.setText("Required phone number ");
                validationEmail.setText("Required email");
                validationGender.setText("Please select gender");
                validationStress.setText("Required address(Street or...)");
                validationDistricts.setText("Required address(Districts or...)");
                validationCommune.setText("Required address(Commune or...)");
                validationCity.setText("Required your city");

            } else {
                if (mFirstName.isEmpty()) {
                    layoutValidationName.setVisibility(View.VISIBLE);
                    validationFirstName.setText("Required first name");
                    editFirstName.setFocusable(true);

                } else if (mLastName.isEmpty()) {
                    layoutValidationName.setVisibility(View.VISIBLE);
                    validationLastName.setText("Required last name");
                    editLastName.setFocusable(true);

                } else if (mGender.equals("null")) {
                    validationGender.setVisibility(View.VISIBLE);
                    validationGender.setText("Please select gender");
                    validationGender.setFocusable(true);

                } else if (mPhone.isEmpty()) {
                    validationPhone.setVisibility(View.VISIBLE);
                    validationPhone.setText("Required phone number");
                    editPhone.setFocusable(true);

                } else if (mEmail.isEmpty()) {
                    validationEmail.setVisibility(View.VISIBLE);
                    validationEmail.setText("Required email");
                    editEmail.setFocusable(true);

                } else if (!isEmailValid(mEmail)) {
                    validationEmail.setVisibility(View.VISIBLE);
                    validationEmail.setText("Invalid email(example@domain.com");
                    editEmail.setFocusable(true);

                } else if (addressStreet.isEmpty()) {
                    layoutValidationAddress1.setVisibility(View.VISIBLE);
                    validationStress.setText("Required address(Street or...)");
                    editAddressStress.setFocusable(true);

                } else if (addressCommune.isEmpty()) {
                    layoutValidationAddress1.setVisibility(View.VISIBLE);
                    validationCommune.setText("Required commune(commune or...)");
                    editAddressCommune.setFocusable(true);

                } else if (addressDistricts.isEmpty()) {
                    layoutValidationAddress2.setVisibility(View.VISIBLE);
                    validationCommune.setText("Required address(Commune or...)");
                    editAddressDistricts.setFocusable(true);

                } else if (addressCity.isEmpty()) {
                    layoutValidationAddress2.setVisibility(View.VISIBLE);
                    validationCity.setText("Required city");
                    editAddressCity.setFocusable(true);
                } else {
                    String mergeAddress = addressStreet + ", " + addressCommune + ", " + addressDistricts;
                    if (InternetConnection.isNetworkConnected(getApplicationContext())) {
                        uploadInformation(userAccessToken, mFirstName, mLastName, mGender, mPhone, mEmail, mergeAddress, addressCity, "not_yet_do_iamge.png");
                    } else {
                        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void uploadInformation(String accessToken, String firstName, String lastName, String gender, String contactPhone, String contactEmail, String address, String city, String profileImage) {
        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        Call<UserInformationResponse> call = apiService.updateUser(accessToken, firstName, lastName, gender, contactPhone, contactEmail, address, city, profileImage);
        call.enqueue(new Callback<UserInformationResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserInformationResponse> call, @NonNull Response<UserInformationResponse> response) {
                if (response.isSuccessful()) {
                    userInformationManager.saveInformation(response.body());
                    userInformationManager.saveAccessToken(accessToken);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } else if (response.code() == 401) {

                    Toast.makeText(RegisterUserActivity.this, "Account is unavailable please try again", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), StartLoginActivity.class));


                } else {
                    Log.e("pppp", response.code() + " = " + response.message());
                    Toast.makeText(RegisterUserActivity.this, "Server problem", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<UserInformationResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("pppp", t.getMessage());
                Toast.makeText(RegisterUserActivity.this, "Failed Connection", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getDataFromIntent() {
        userAccessToken = getIntent().getStringExtra(IntentData.ACCESS_TOKEN);
        userPhone = getIntent().getStringExtra(IntentData.PHONE);
        if (userPhone != null) {
            editPhone.setText(userPhone);
        }
    }


    private void setUpSpinnerGender() {

        String[] ITEMS = {"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void clearTextValidation() {

        validationFirstName.setText("");
        validationLastName.setText("");
        validationPhone.setText("");
        validationEmail.setText("");
        validationGender.setText("");
        validationStress.setText("");
        validationDistricts.setText("");
        validationCommune.setText("");
        validationCity.setText("");

    }

    private void hideLayout(boolean visible) {
        if (visible) {
            layoutValidationName.setVisibility(View.VISIBLE);
            validationPhone.setVisibility(View.VISIBLE);
            validationEmail.setVisibility(View.VISIBLE);
            validationGender.setVisibility(View.VISIBLE);
            layoutValidationAddress1.setVisibility(View.VISIBLE);
            layoutValidationAddress2.setVisibility(View.VISIBLE);

        } else {
            layoutValidationName.setVisibility(View.GONE);
            validationPhone.setVisibility(View.GONE);
            validationEmail.setVisibility(View.GONE);
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
        editPhone = findViewById(R.id.editContactNumber);
        editEmail = findViewById(R.id.editEmail);
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
        validationEmail = findViewById(R.id.textValidationEmail);
        validationStress = findViewById(R.id.textValidationAddressStreet);
        validationCommune = findViewById(R.id.textValidationAddressCommune);
        validationDistricts = findViewById(R.id.textValidationAddressDistrict);
        validationCity = findViewById(R.id.textValidationAddressCity);
        validationPhone = findViewById(R.id.textValidationPhone);

        hideLayout(false);
        clearTextValidation();


    }


}
