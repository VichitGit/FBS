package vichitpov.com.fbs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import vichitpov.com.fbs.R;

public class EditUserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textAddress;
    private int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        textAddress = findViewById(R.id.text_address);


        textAddress.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            Log.e("pppp", e.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getApplicationContext(), data);
                String address = place.getAddress().toString();

                Log.e("pppp Address: ", address);
                Log.e("pppp ID:", place.getId());
                Log.e("pppp Latitude:", place.getLatLng().latitude + "");
                Log.e("pppp Longitude:", place.getLatLng().longitude + "");
                Log.e("pppp PhoneNumber:", place.getPhoneNumber().toString());
                Log.e("pppp PlaceTypes:", place.getPlaceTypes().toString());

                textAddress.setText(address);
            }
        }
    }
}
