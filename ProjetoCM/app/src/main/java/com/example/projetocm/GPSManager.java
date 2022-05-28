package com.example.projetocm;

import static android.content.Context.LOCATION_SERVICE;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Calendar;
import java.util.List;

public class GPSManager {

    private static GPSManager instance;

    private LocationManager mLocationManager;

    private GPSManager() {

    }

    public static GPSManager getInstance() {
        if (instance == null) instance = new GPSManager();
        return instance;
    }

    /*public Location getLocation(Activity act) {


        ActivityCompat.requestPermissions(act, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, 200);

        if (ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
        ) {
            return null;
        }

        LocationManager locManager =
                (LocationManager) act.getSystemService(LOCATION_SERVICE);

        Location location = locManager.getLastKnownLocation("gps");

        Log.d("DEBUG", "\n" + String.valueOf(locManager) + "\n" + String.valueOf(location));

        return location;
    }

*/
    private Location implGetLocation(Activity act){
        mLocationManager = (LocationManager) act.getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                // return null;

                ActivityCompat.requestPermissions(act, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 420);
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public Location getLocation(Activity act) {
        if (!Perms.has(act, Perms.COARSE_LOCATION, Perms.FINE_LOCATION)){
            ActivityCompat.requestPermissions(act,
                    new String[] {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    420);
        }

        mLocationManager =
                (LocationManager) act
                .getApplicationContext()
                .getSystemService(LOCATION_SERVICE);

        return mLocationManager.getLastKnownLocation("gps");
    }

}
