package com.example.projetocm;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.SharedPreferencesCompat;
import androidx.core.content.SharedPreferencesKt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
 
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.widget.ImageView;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    DAOUser daoUser = new DAOUser();
    private List<User> topGuides;
    private ListView simpleList;

    private DAOUser userDB;
    private boolean isGuide;
    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        userDB = new DAOUser();
    //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        topGuides = new ArrayList<>();

        HomeActivity _this = this;

        SharedPreferences preferences = getSharedPreferences("userDefinitions", MODE_PRIVATE);
        String isGuideString = preferences.getString("isGuide", "");

        GPSManager gpsManager = new GPSManager();


        GPSManager.lastLocation = gpsManager.getLocationWithPermission(this);
        //Toast.makeText(this, String.valueOf(loc), Toast.LENGTH_LONG).show();

        simpleList = findViewById(R.id.topGuidesListView);
        userDB.setUserAttributeValue(
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                "geolocation",
                new SimpleLocation(
                        GPSManager.lastLocation.getLatitude(),
                        GPSManager.lastLocation.getLongitude()
                )
        );
        isGuide = isGuideString.equals("true");
        daoUser.getDataSnapshotOnce(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                double rankingValues[] = new double[3];
                for(int i=0;i<3;i++) {
                    User curBestGuide = null;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        if(data.child("userGuide").getValue(Boolean.class)){
                            double curUserRating =data.child("ratingScore").getValue(Double.class);
                            double curUserNumOfPplHelped = data.child("numOfPplHelped").getValue(Double.class);
                            double curRankingValue = curUserRating+curUserNumOfPplHelped*2;

                            if(rankingValues[i]<curRankingValue){
                                if(i!=0) {
                                    if(!(curRankingValue>=rankingValues[i-1])){
                                        rankingValues[i] = curRankingValue;
                                        curBestGuide = data.getValue(User.class);
                                        curBestGuide.setUserKey(data.getKey());
                                    }
                                }else{
                                    rankingValues[i] = curRankingValue;
                                    curBestGuide = data.getValue(User.class);
                                    curBestGuide.setUserKey(data.getKey());
                                }
                            }
                        }
                    }
                    topGuides.add(curBestGuide);
                    curBestGuide = null;
                }

                List<User> removeList = new ArrayList<>();
                for(User guide: topGuides){
                    if(guide==null){
                        removeList.add(guide);
                    }
                }
                topGuides.removeAll(removeList);

                ListAdapterBestGuide listAdapterBestGuide = new ListAdapterBestGuide(getApplicationContext(), R.layout.best_guide_list_element, topGuides);
                simpleList.setAdapter(listAdapterBestGuide);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


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

        /*searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGuide) {
                    Toast.makeText (
                            _this,
                            "Will open a "
                                    + (isGuide ? ("Guide") : ("Refugee"))
                                    + "'s screen.",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });*/
}
