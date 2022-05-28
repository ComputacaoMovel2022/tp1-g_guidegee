package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        ImageView editProfileButton = findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, EditProfile.class));
                finish();
            }
        });

        SharedPreferences preferences = getSharedPreferences("userDefinitions", MODE_PRIVATE);
        String isGuideString = preferences.getString("isGuide", "");
        if (isGuideString.equals("true")) {
            editProfileButton.setVisibility(View.VISIBLE);
        } else {
            editProfileButton.setVisibility(View.INVISIBLE);
        }
    }
}