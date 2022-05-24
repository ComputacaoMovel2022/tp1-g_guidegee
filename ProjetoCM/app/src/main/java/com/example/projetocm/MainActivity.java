package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_history);
        Button reviewGuideButton = (Button) findViewById(R.id.buttonReview1);
        reviewGuideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itRatingGuide = new Intent(MainActivity.this,RatingGuide.class);
                itRatingGuide.putExtra("guideName","Ricardo");
                startActivity(itRatingGuide);
            }
        });

    }

//    public void openRatingPage(View v){
//        Intent itRatingGuide = new Intent(this,RatingGuide.class);
//        itRatingGuide.putExtra("guideName","Ricardo");
//        startActivity(itRatingGuide);
//    }

    
}