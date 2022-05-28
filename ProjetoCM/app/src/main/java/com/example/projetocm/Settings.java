package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        DAOUser daoUser = new DAOUser();

        /**
        * Logout Account
        */
        Button logout= (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("rememberMe", "false").apply();
                finishAffinity();
                Intent intent = new Intent(getApplicationContext(), StartPageActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Delete Account
         */
        Button deleteAccount= (Button)findViewById(R.id.DeleteAccount);
        deleteAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Delete Account
                //maybe a pop-up to be sure
                daoUser.removeUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
            }
        });

        /**
         * Activate/Deactivate Notification
         */
        Switch notificationSwitch = (Switch)  findViewById(R.id.Notification);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Deactivate Notifications
                }else{
                    //Activate Notifications
                }
            }

        });

        /**
         * Activate/Deactivate Availability
         */
        Switch availabilitySwitch = (Switch)  findViewById(R.id.AvailabilityChoice);
        availabilitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Activate availability
                }else{
                    //Deactivate availability
                }
            }

        });

        /**
         * choose the Distance
         */
        Spinner distanceChoiceSpinner = (Spinner)  findViewById(R.id.DistanceChoice);
        distanceChoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Change the distance in DB
            }
            //bugs without this part
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        /**
         * choose the Unity
         */
        Spinner unityChoiceSpinner = (Spinner)  findViewById(R.id.UnityChoice);
        unityChoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Change the distance in DB, kilometer(KM) or miles(mi)
            }
            //bugs without this part
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        /**
         * choose the Language
         */
        Spinner languageChoiceSpinner = (Spinner)  findViewById(R.id.LanguageChoice);
        languageChoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Change the xml string file
            }
            //bugs without this part
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        /**
         * Activate/Deactivate Darkmode
         */
        Switch darkModeSwitch = (Switch)  findViewById(R.id.DarkMode);
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //DarkMode activated
                }else{
                    //DarkMode Deactivated
                }
            }

        });
    }
}