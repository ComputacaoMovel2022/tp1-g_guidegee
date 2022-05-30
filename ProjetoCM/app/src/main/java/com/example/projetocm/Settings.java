package com.example.projetocm;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import java.util.Locale;

public class Settings extends AppCompatActivity{

    private boolean sensorActive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_settings);
        DAOUser daoUser = new DAOUser();
        SharedPreferences preferences = getSharedPreferences("userDefinitions", MODE_PRIVATE);
        String isDarkModeOn = preferences.getString("darkMode", "");
        String whatUnity = preferences.getString("unity", "");
        String whatDistance = preferences.getString("distance", "");
        String whatLanguage = preferences.getString("language", "");
        String whatAvailability = preferences.getString("availability", "");
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
         * Activate/Deactivate Availability
         */
        Switch availabilitySwitch = (Switch)  findViewById(R.id.AvailabilityChoice);
        if(whatAvailability.equals("true")){
            availabilitySwitch.setChecked(true);
        }else{
            availabilitySwitch.setChecked(false);
        }
        availabilitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Activate availability
                    daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "availability", true);
                    preferences.edit().putString("availability", "true").apply();
                }else{
                    //Deactivate availability
                    daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "availability", false);
                    preferences.edit().putString("availability", "false").apply();
                }
            }

        });

        /**
         * choose the Distance
         */
        Spinner unityChoiceSpinner = (Spinner)  findViewById(R.id.UnityChoice);
        Spinner distanceChoiceSpinner = (Spinner)  findViewById(R.id.DistanceChoice);
        if(whatDistance!=null && whatDistance!=""){
            distanceChoiceSpinner.setSelection(parseInt(whatDistance));
        }
        if(whatUnity!=null && whatUnity!=""){
            unityChoiceSpinner.setSelection(parseInt(whatUnity));
        }
        distanceChoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Change the distance in DB
                String unity = unityChoiceSpinner.getSelectedItem().toString();
                switch (i){
                    case 0:
                        //1
                        preferences.edit().putString("distance", "0").apply();
                        if(unity.equals("Km")) {
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 1000);
                        }else{
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 1609);
                        }
                        break;
                    case 1:
                        //2
                        if(unity.equals("Km")) {
                            preferences.edit().putString("distance", "1").apply();
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 2000);
                        }else{
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 3218);
                        }
                        break;
                    case 2:
                        //5
                        preferences.edit().putString("distance", "2").apply();
                        if(unity.equals("Km")) {
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 5000);
                        }else{
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 8050);
                        }
                        break;
                    case 3:
                        //7
                        preferences.edit().putString("distance", "3").apply();
                        if(unity.equals("Km")) {
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 7000);
                        }else{
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 11270);
                        }
                        break;
                    case 4:
                        //10
                        preferences.edit().putString("distance", "4").apply();
                        if(unity.equals("Km")) {
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 10000);
                        }else{
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 16090);
                        }
                        break;
                    case 5:
                        //15
                        preferences.edit().putString("distance", "5").apply();
                        if(unity.equals("Km")) {
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 15000);
                        }else{
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 24140);
                        }
                        break;
                    case 6:
                        //20
                        preferences.edit().putString("distance", "6").apply();
                        if(unity.equals("Km")) {
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 20000);
                        }else{
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 32190);
                        }
                        break;
                    case 7:
                        //25
                        preferences.edit().putString("distance", "7").apply();
                        if(unity.equals("Km")) {
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 25000);
                        }else{
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 40230);
                        }
                        break;
                    case 8:
                        //30
                        preferences.edit().putString("distance", "8").apply();
                        if(unity.equals("Km")) {
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 30000);
                        }else{
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(), "guideDistanceThreshold", 48280);
                        }
                        break;
                }
            }
            //bugs without this part
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        /**
         * choose the Unity
         * Since we covert to km to the db i believe this is not necessary
         */
        unityChoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        //km
                        preferences.edit().putString("unity", "0").apply();
                        break;
                    case 1:
                        //miles
                        preferences.edit().putString("unity", "1").apply();
                        break;
                }
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
        languageChoiceSpinner.setSelection(languageChoiceSpinner.getSelectedItemPosition(), false);
        if(whatLanguage!=null && whatLanguage!=""){
            languageChoiceSpinner.setSelection(parseInt(whatLanguage));
        }
        languageChoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Change the xml string file
                switch (i){
                    case 0:
                        preferences.edit().putString("language", "0").apply();
                        setLocale(getApplicationContext(), "en");
                        //recreate();
                        break;
                    case 1:
                        preferences.edit().putString("language", "1").apply();
                        setLocale(getApplicationContext(), "pt");
                        //recreate();
                        break;
                }
            }
            //bugs without this part
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        /**
         * Activate/Deactivate Darkmode
         */
        SensorManager sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Switch darkModeSwitch = (Switch)  findViewById(R.id.DarkMode);
        if(isDarkModeOn.equals("true")){
            darkModeSwitch.setChecked(true);
        }else{
            darkModeSwitch.setChecked(false);
        }
        if(sensor != null){
            sensorManager.registerListener(
                    lightSensorListener,
                    sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        }
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //Dark mode change xml (colors) in onSensorChange
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(sensor != null){
                    if(isChecked){
                        //DarkMode activated
                        sensorActive=true;
                        preferences.edit().putString("darkMode", "true").apply();
                    }else{
                        sensorActive=false;
                        //DarkMode Deactivated
                        preferences.edit().putString("darkMode", "false").apply();
                    }
                }
            }

        });
    }

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";
    public static Context setLocale(Context context, String language) {
        persist(context, language);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }
        return updateResourcesLegacy(context, language);
    }
    private static void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }
    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    public void goBackClick(View v) {
        startActivity(new Intent(Settings.this, HomeActivity.class));
        finish();
    }



    private final SensorEventListener lightSensorListener
            = new SensorEventListener(){

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT && sensorActive){
                if(sensorEvent.values[0] <= 10000){
                    //activate dark mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    //activate default mode/dark mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };
}