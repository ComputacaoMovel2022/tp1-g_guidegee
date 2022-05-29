package com.example.projetocm;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.SharedPreferencesCompat;
import androidx.core.content.SharedPreferencesKt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.util.SharedPreferencesUtils;
import com.google.api.Property;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class HomeActivity extends AppCompatActivity {

    int[] profilePictures = {R.drawable.test_profile_pic, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon};
    String[] profileNames = {"Mariana Gustavo", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio"};
    ListView simpleList;

    private DAOUser userDB;
    private boolean isGuide;
    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        userDB = new DAOUser();

        HomeActivity _this = this;

        SharedPreferences preferences = getSharedPreferences("userDefinitions", MODE_PRIVATE);
        String isGuideString = preferences.getString("isGuide", "");

        GPSManager gpsManager = new GPSManager();

        gpsManager.requestPermission(_this);

        //userDB.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "geolocation", loc);
        isGuide = isGuideString.equals("true");
        /*
        Toast.makeText (
                _this,
                "Logged as "
                        + (isGuide ? ("Guide") : ("Refugee"))
                        + " at "
                        + ((loc == null) ? "Unset location" : loc.toString()),
                Toast.LENGTH_LONG
        ).show();
        */

    }

    public void clickProfile(View view) {
        startActivity(new Intent(HomeActivity.this, EditProfile.class));
        finish();
    }
    public void clickOnMessagesImage(View view) {
        Intent intent = new Intent(getApplicationContext(), MessageListPage.class);
        startActivity(intent);
    }
}