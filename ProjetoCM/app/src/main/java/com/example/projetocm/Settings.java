package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Display;
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
import com.google.rpc.context.AttributeContext;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Locale;

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
        languageChoiceSpinner.setSelection(languageChoiceSpinner.getSelectedItemPosition(), false);
        languageChoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Change the xml string file
                switch (i){
                    case 0:
                        setLocale(getApplicationContext(), "en");
                        //recreate();
                        break;
                    case 1:
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
}