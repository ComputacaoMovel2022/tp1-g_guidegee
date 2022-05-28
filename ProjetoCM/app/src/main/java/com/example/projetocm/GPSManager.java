package com.example.projetocm;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import java.util.Calendar;

public class GPSManager {

    private static GPSManager instance;

    private GPSManager() {

    }

    public static GPSManager getInstance() {
        if (instance == null) instance = new GPSManager();
        return instance;
    }

    public Location getLocation(Activity act) {
        LocationManager locManager =
                (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);

        ActivityCompat.requestPermissions(act, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, 200);

        if ( ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
          && ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
        ) {
            return null;
        }

        Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return location;
    }

}
