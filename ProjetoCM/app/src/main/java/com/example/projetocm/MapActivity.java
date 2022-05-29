package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private double mLat, mLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mLat = savedInstanceState.getDouble("myLatitude", 0.0);
        mLong = savedInstanceState.getDouble("myLongitude", 0.0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng pos = new LatLng(mLat, mLong);
        mMap.addMarker(new MarkerOptions().position(pos).title("You are here"));
        mMap.addCircle(
                new CircleOptions()
                        .center(pos)
                        .radius(10000)
                        .fillColor(getColor(R.color.invalid_map_circle_fill))
                        .strokeColor(getColor(R.color.invalid_map_circle_border))
        );

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 10.0f));

        Toast.makeText(this, "a", Toast.LENGTH_LONG).show();
    }

}