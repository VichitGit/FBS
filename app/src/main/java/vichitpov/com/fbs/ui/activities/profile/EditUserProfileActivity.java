package vichitpov.com.fbs.ui.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.constant.AnyConstant;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.UserInformationResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activities.login.StartLoginActivity;

public class EditUserProfileActivity extends AppCompatActivity {
    private EditText editFirstName, editLastName, editAddress, editCity, editDescription;
    private UserInformationManager userInformationManager;
    private Button buttonSave;
    private SpotsDialog dialog;
    private ImageView imageBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editAddress = findViewById(R.id.editAddress);
        editCity = findViewById(R.id.editCity);
        buttonSave = findViewById(R.id.buttonSave);
        editDescription = findViewById(R.id.editDescription);
        imageBack = findViewById(R.id.imageBack);

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        dialog = new SpotsDialog(this, "Updating...");

        getSharePreferences();

        listener();

    }

    private void listener() {
        imageBack.setOnClickListener(view -> finish());
        buttonSave.setOnClickListener(view -> {
            if (checkValidation()) {
                ApiService apiService = ServiceGenerator.createService(ApiService.class);
                String accessToken = userInformationManager.getUser().getAccessToken();

                if (!accessToken.equals("N/A")) {
                    dialog.show();
                    Call<UserInformationResponse> call = apiService.updateUser(accessToken, editFirstName.getText().toString(),
                            editLastName.getText().toString(), userInformationManager.getUser().getGender(), editAddress.getText().toString(), editCity.getText().toString(), editDescription.getText().toString());

                    call.enqueue(new Callback<UserInformationResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<UserInformationResponse> call, @NonNull Response<UserInformationResponse> response) {
                            if (response.isSuccessful()) {
                                userInformationManager.saveInformation(response.body());
                                dialog.dismiss();
                                Intent intent = new Intent();
                                setResult(AnyConstant.EDIT_PROFILE_CODE, intent);
                                finish();

                            } else if (response.code() == 401) {
                                dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), StartLoginActivity.class));
                                finish();

                            } else {
                                dialog.dismiss();
                                //Log.e("pppp else", response.code() + " = " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<UserInformationResponse> call, @NonNull Throwable t) {
                            dialog.dismiss();
                            t.printStackTrace();
                            Log.e("pppp onFailure", t.getMessage());

                        }
                    });

                }
            }

        });
    }


    private boolean checkValidation() {
        if (editFirstName.getText().toString().isEmpty() && editLastName.getText().toString().isEmpty() && editDescription.getText().toString().isEmpty() && editAddress.getText().toString().isEmpty() && editCity.getText().toString().isEmpty()) {
            editFirstName.setError("Required first name");
            editFirstName.requestFocus();
            editLastName.setError("Required last name");
            editAddress.setError("Required address");
            editCity.setError("Required city");
            editDescription.setError("Required description");
            return false;
        } else {
            if (editFirstName.getText().toString().isEmpty()) {
                editFirstName.requestFocus();
                editFirstName.setError("Required first name");

            } else if (editLastName.getText().toString().isEmpty()) {
                editLastName.requestFocus();
                editLastName.setError("Required last name");

            } else if (editDescription.getText().toString().isEmpty()) {
                editAddress.requestFocus();
                editDescription.setError("Required address");

            } else if (editAddress.getText().toString().isEmpty()) {
                editAddress.requestFocus();
                editAddress.setError("Required address");

            } else if (editCity.getText().toString().isEmpty()) {
                editCity.requestFocus();
                editCity.setError("Required city");

            } else {
                return true;
            }
            return false;
        }
    }


    private void getSharePreferences() {
        String pFirstName = userInformationManager.getUser().getFirstName();
        String pLastName = userInformationManager.getUser().getLastName();
        String pAddress = userInformationManager.getUser().getAddress();
        String pCity = userInformationManager.getUser().getCity();
        String description = userInformationManager.getUser().getDescription();

        if (!pFirstName.equals("N/A") && !pLastName.equals("N/A")) {
            editFirstName.setText(pFirstName);
            editLastName.setText(pLastName);
        }

        if (!pAddress.equals("N/A")) {
            editAddress.setText(pAddress);
        }

        if (!pCity.equals("N/A")) {
            editCity.setText(pCity);
        }
        if (!description.equals("N/A")) {
            editDescription.setText(description);
        }
    }
}
