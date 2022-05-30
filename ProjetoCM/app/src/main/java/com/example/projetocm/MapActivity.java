package com.example.projetocm;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projetocm.databinding.ActivityMapBinding;
import com.example.projetocm.databinding.ActivityMapGreenBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.maps.android.SphericalUtil;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private double mLat, mLong, gLat, gLong;
    private int gRadius;

    private ActivityMapBinding binding;
    private GoogleMap mMap;
    private double distance;
    private LatLng mPos, gPos;
    private String guideKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        guideKey = getIntent().getStringExtra("guideKey");
        mLat = getIntent().getDoubleExtra("myLat", 0.0);
        mLong = getIntent().getDoubleExtra("myLong", 0.0);
        gLat = getIntent().getDoubleExtra("gLat", 0.0);
        gLong = getIntent().getDoubleExtra("gLong", 0.0);
        gRadius = getIntent().getIntExtra("gRadius", 0);
        mPos = new LatLng(mLat, mLong);
        gPos = new LatLng(gLat, gLong);
        distance = SphericalUtil.computeDistanceBetween(mPos, gPos);
        if (distance > gRadius) {
            binding = ActivityMapBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
        } else {
            ActivityMapGreenBinding bindingGreen = ActivityMapGreenBinding.inflate(getLayoutInflater());
            setContentView(bindingGreen.getRoot());
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        int colorFillLayout, colorStrokeLayout;
        if (distance > gRadius) {
            colorFillLayout = R.color.invalid_map_circle_fill;
            colorStrokeLayout = R.color.invalid_map_circle_border;
            Button reviewList = findViewById(R.id.requestButton);

            reviewList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MapActivity.this, GuideListActivity.class));
                }
            });
        } else {
            colorFillLayout = R.color.valid_map_circle_fill;
            colorStrokeLayout = R.color.valid_map_circle_border;

            Button requestButton = findViewById(R.id.requestButton);
            requestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    (new DAOUser()).addUserRequestList(guideKey, FirebaseAuth.getInstance().getCurrentUser().getUid());
                    startActivity(new Intent(MapActivity.this, GuideListActivity.class));
                }
            });
        }
        mMap.addMarker(new MarkerOptions().position(mPos).title("You are here"));
        mMap.addCircle(
                new CircleOptions()
                        .center(gPos)
                        .radius(gRadius)
                        .fillColor(getColor(colorFillLayout))
                        .strokeColor(getColor(colorStrokeLayout))
        );

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mPos, 10.0f));

        Toast.makeText(this, "a", Toast.LENGTH_LONG).show();
    }

}