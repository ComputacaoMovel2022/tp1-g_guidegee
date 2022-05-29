package com.example.projetocm;

import static android.content.Context.LOCATION_SERVICE;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.type.LatLng;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class GPSManager {

    private LocationManager mLocationManager;

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
    public Location implGetLocation(Activity act){
        mLocationManager = (LocationManager) act.getApplicationContext().getSystemService(LOCATION_SERVICE);
        Location bestLocation = null;
        System.out.println("BEFORE FOR");
            if (ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                // return null;
                System.out.println("REQUESTING PERMISSION");
                ActivityCompat.requestPermissions(act, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 99);
            }
            Criteria criteria = new Criteria();
            Location location = mLocationManager.getLastKnownLocation(mLocationManager.getBestProvider(criteria, false));
            if (location != null) {
                double lat = location.getLatitude();
                double longi = location.getLongitude();
                System.out.println("LOCATION LAT: " + lat + " LONG: " + longi);
                return location;
            }
            return null;
                /*
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }*/
    }


    public Location getLocationWithPermission(AppCompatActivity act) {

        AtomicReference<Location> locAtomic = new AtomicReference<>();

        AtomicInteger perms = new AtomicInteger(Perms.NO_LOCATION);

        ActivityResultContracts.RequestMultiplePermissions multiplePermissionsContract
                = new ActivityResultContracts.RequestMultiplePermissions();

        ActivityResultLauncher<String[]> locPermsReq =
                act.registerForActivityResult(multiplePermissionsContract, isGranted -> {

                    boolean allPermissionsGranted = true;
                    for (String s: isGranted.keySet()) {
                        if (!isGranted.get(s)) {
                            allPermissionsGranted = false;
                        }
                    }

                    if (allPermissionsGranted) {
                        act.setContentView(R.layout.activity_home);
                        locAtomic.set(implGetLocation(act));

                        /*Toast.makeText (
                                act,
                                "Logged at "
                                        + ((locAtomic.get() == null) ? "Unset location" : locAtomic.get().toString()),
                                Toast.LENGTH_LONG
                        ).show();*/
                    }

                    /*Log.d("PERMISSIONS", "Launcher result: " + isGranted.toString());
                    if (isGranted.containsValue(false)) {
                        Log.d("PERMISSIONS", "At least one of the permissions was not granted, launching again...");
                        //locPermsReq.launch(PERMISSIONS);
                    }*/
                });

        if (ContextCompat.checkSelfPermission(act.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
          || ContextCompat.checkSelfPermission(act.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("CONCEDING PERMISSION....");
            locPermsReq.launch(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION});
        } else {
            System.out.println("PERMISSION HAS ALREADY BEEN GIVEN");
            act.setContentView(R.layout.activity_home);
            if (locAtomic.get() == null) locAtomic.set(implGetLocation(act));
        }

        return locAtomic.get();
    }

    public Location getLocationWithPermission(FragmentActivity act) {

        AtomicReference<Location> locAtomic = new AtomicReference<>();

        AtomicInteger perms = new AtomicInteger(Perms.NO_LOCATION);

        ActivityResultContracts.RequestMultiplePermissions multiplePermissionsContract
                = new ActivityResultContracts.RequestMultiplePermissions();

        ActivityResultLauncher<String[]> locPermsReq =
                act.registerForActivityResult(multiplePermissionsContract, isGranted -> {

                    boolean allPermissionsGranted = true;
                    for (String s: isGranted.keySet()) {
                        if (!isGranted.get(s)) {
                            allPermissionsGranted = false;
                        }
                    }

                    if (allPermissionsGranted) {
                        act.setContentView(R.layout.activity_home);
                        locAtomic.set(implGetLocation(act));

                        /*Toast.makeText (
                                act,
                                "Logged at "
                                        + ((locAtomic.get() == null) ? "Unset location" : locAtomic.get().toString()),
                                Toast.LENGTH_LONG
                        ).show();*/
                    }

                    /*Log.d("PERMISSIONS", "Launcher result: " + isGranted.toString());
                    if (isGranted.containsValue(false)) {
                        Log.d("PERMISSIONS", "At least one of the permissions was not granted, launching again...");
                        //locPermsReq.launch(PERMISSIONS);
                    }*/
                });

        if (ContextCompat.checkSelfPermission(act.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(act.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("CONCEDING PERMISSION....");
            locPermsReq.launch(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION});
        } else {
            System.out.println("PERMISSION HAS ALREADY BEEN GIVEN");
            act.setContentView(R.layout.activity_home);
            if (locAtomic.get() == null) locAtomic.set(implGetLocation(act));
        }

        return locAtomic.get();
    }
}
