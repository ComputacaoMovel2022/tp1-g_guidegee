package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class RatingGuide extends AppCompatActivity {
    //RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar);
    //Button submitButton = (Button) findViewById(R.id.submitButton);
    String guideName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_guide);
        getSupportActionBar().hide();

        Intent it = getIntent();
        guideName = it.getStringExtra("guideName");




        // initiate rating bar and a button
        final RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar);
        Button submitButton = (Button) findViewById(R.id.submitButton);
        Button backButton = (Button) findViewById(R.id.buttonBack);
        // perform click event on button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float ratingValue = simpleRatingBar.getRating();

                Toast.makeText(getApplicationContext(),
                        "Nome do Guia:"+guideName,
                        Toast.LENGTH_LONG).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent guideHistory = new Intent(RatingGuide.this,MainActivity.class);
                startActivity(guideHistory);
            }
        });


    }

    //getRating()
    //getNumStars()


}