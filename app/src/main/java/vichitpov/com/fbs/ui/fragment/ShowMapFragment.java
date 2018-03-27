package vichitpov.com.fbs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import vichitpov.com.fbs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowMapFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private double latitude, longitude;

    public ShowMapFragment() {
        //
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String latLng = getArguments().getString("LatLng");
        if (latLng != null) {

            String mLatitude = latLng.substring(0, latLng.indexOf(","));
            String mLongitude = latLng.substring(latLng.lastIndexOf(",") + 1);

            latitude = Double.parseDouble(mLatitude);
            longitude = Double.parseDouble(mLongitude);
        }

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(latitude, longitude);
        //pin position
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("V-Shop"));
        //add circle around marker
        mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(200)
                .strokeColor(R.color.colorPrimary));

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        mMap.setIndoorEnabled(true);

        //zoom to current location if user enable gps
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

    }
}
