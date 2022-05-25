package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.material.navigation.NavigationBarView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        /**
         * Delete Account
         */
        Button deleteAccount= (Button)findViewById(R.id.DeleteAccount);
        deleteAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Delete Account
                //maybe a pop-up to be sure
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