package vichitpov.com.fbs.ui.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

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
import vichitpov.com.fbs.ui.fragment.ShowMapFragment;

public class EditUserProfileActivity extends AppCompatActivity {
    private EditText editFirstName, editLastName, editAddress, editCity, editDescription;
    private UserInformationManager userInformationManager;
    private Button buttonSave;
    private SpotsDialog dialog;
    private ImageView imageBack;
    private ShowMapFragment showMapFragment;
    private TextView textChange;
    private String latitudeLongtitude;
    private String name;


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
        textChange = findViewById(R.id.textChange);

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        dialog = new SpotsDialog(this, getString(R.string.alert_dialog_updating));

        getSharePreferences();

        listener();

    }

    private void listener() {
        imageBack.setOnClickListener(view -> {
            finish();
        });
        textChange.setOnClickListener(view -> openMapPicker());

        buttonSave.setOnClickListener(view -> {
            if (checkValidation()) {
                update();
            }
        });


    }

    //open default google's activity map that config in manifests
    private void openMapPicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), AnyConstant.PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AnyConstant.PLACE_PICKER_REQUEST && resultCode != RESULT_CANCELED) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                latitudeLongtitude = place.getLatLng().latitude + "," + place.getLatLng().longitude;
                showMapAndSendCoordinate(latitudeLongtitude, name);
//                String address = String.format(place.getAddress().toString());

//                Log.e("pppp Address: ", address);
//                Log.e("pppp ID:", place.getId());
//                Log.e("pppp Latitude:", place.getLatLng().latitude + "");
//                Log.e("pppp Longitude:", place.getLatLng().longitude + "");
//                Log.e("pppp PhoneNumber:", place.getPhoneNumber().toString());
//                Log.e("pppp PlaceTypes:", place.getPlaceTypes().toString());

            }
        }
    }

    //update
    private void update() {
        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        String accessToken = userInformationManager.getUser().getAccessToken();
        if (!accessToken.equals("N/A")) {
            dialog.show();
            Call<UserInformationResponse> call = apiService.updateUser(accessToken, editFirstName.getText().toString(),
                    editLastName.getText().toString(), userInformationManager.getUser().getGender(), editAddress.getText().toString(),
                    editCity.getText().toString(), editDescription.getText().toString(), latitudeLongtitude);

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

    //validation input
    private boolean checkValidation() {
        if (editFirstName.getText().toString().isEmpty() && editLastName.getText().toString().isEmpty() && editDescription.getText().toString().isEmpty() && editAddress.getText().toString().isEmpty() && editCity.getText().toString().isEmpty()) {
            editFirstName.setError(getString(R.string.validation_first_name));
            editFirstName.requestFocus();
            editLastName.setError(getString(R.string.validation_last_name));
            editAddress.setError(getString(R.string.validation_address));
            editCity.setError(getString(R.string.validation_city));
            editDescription.setError(getString(R.string.validation_description));
            return false;
        } else {
            if (editFirstName.getText().toString().isEmpty()) {
                editFirstName.requestFocus();
                editFirstName.setError(getString(R.string.validation_first_name));

            } else if (editLastName.getText().toString().isEmpty()) {
                editLastName.requestFocus();
                editLastName.setError(getString(R.string.validation_last_name));

            } else if (editDescription.getText().toString().isEmpty()) {
                editAddress.requestFocus();
                editDescription.setError(getString(R.string.validation_address));

            } else if (editAddress.getText().toString().isEmpty()) {
                editAddress.requestFocus();
                editAddress.setError(getString(R.string.validation_address));

            } else if (editCity.getText().toString().isEmpty()) {
                editCity.requestFocus();
                editCity.setError(getString(R.string.validation_city));

            } else {
                return true;
            }
            return false;
        }
    }

    //get share preferences
    private void getSharePreferences() {
        String pFirstName = userInformationManager.getUser().getFirstName();
        String pLastName = userInformationManager.getUser().getLastName();
        String pAddress = userInformationManager.getUser().getAddress();
        String pCity = userInformationManager.getUser().getCity();
        String description = userInformationManager.getUser().getDescription();
        latitudeLongtitude = userInformationManager.getUser().getMapCondinate();

        name = pFirstName + " " + pLastName;
        showMapAndSendCoordinate(latitudeLongtitude, name);

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

    //show fragment map and send string of coordinate to fragment map
    private void showMapAndSendCoordinate(String coordinateMap, String name) {
        showMapFragment = new ShowMapFragment();
        Bundle bundle = new Bundle();
        bundle.putString("LatLng", coordinateMap);
        bundle.putString("Name", name);
        showMapFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.linearMap, showMapFragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
