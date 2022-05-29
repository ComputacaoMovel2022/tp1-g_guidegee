package com.example.projetocm;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.util.Arrays;

public class Perms {

    public static final int NO_LOCATION = 0;
    public static final int COARSE_LOCATION = 1;
    public static final int FINE_LOCATION = 2;
    public static final int ALL_LOCATION = 3;

    public static boolean has(Activity act, String perm) {
        return ActivityCompat.checkSelfPermission(act, perm) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean has(Activity act, String... perms) {
        for (String s: perms) {
            if (ActivityCompat.checkSelfPermission(act, s) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }
}
