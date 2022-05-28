package com.example.projetocm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.SharedPreferencesCompat;
import androidx.core.content.SharedPreferencesKt;

import android.content.Intent;
import android.os.Bundle;
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
        setContentView(R.layout.activity_home);

        userDB = new DAOUser();

        HomeActivity _this = this;
        userDB.getDataSnapshotOnce(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DataSnapshot loggedUserEntry = snapshot.child(id);
                isGuide = loggedUserEntry.child("userGuide").getValue(Boolean.class);

                Location loc = GPSManager.getInstance().getLocation(_this);

                userDB.setUserAttributeValue(id, "geolocation", loc);

                Toast.makeText (
                        _this,
                        "Logged as "
                                + (isGuide ? ("Guide") : ("Refugee"))
                                + " at "
                                + loc.toString(),
                        Toast.LENGTH_LONG
                ).show();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (isGuide) {

        }
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