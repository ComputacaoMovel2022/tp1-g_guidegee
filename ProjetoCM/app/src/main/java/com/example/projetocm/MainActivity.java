package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {


    int[] profilePictures = {R.drawable.ellipse_20, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon};
    String[] profileNames = {"João Morais", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio"};
    ListView simpleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_history);
        getSupportActionBar().hide();

        //Lista de Guias
        simpleList = findViewById(R.id.guidesListView);
        SimplifiedProfileItem simplifiedProfileItem = new SimplifiedProfileItem(getApplicationContext(), R.layout.guide_history_list_element, profileNames, profilePictures);
        simpleList.setAdapter(simplifiedProfileItem);

    /*
        //Butão Review
        Button reviewGuideButton = (Button) findViewById(R.id.buttonReview1);
        reviewGuideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itRatingGuide = new Intent(MainActivity.this,RatingGuide.class);
                itRatingGuide.putExtra("guideName","Ricardo");
                startActivity(itRatingGuide);
            }
        });
    */


    }



//    public void openRatingPage(View v){
//        Intent itRatingGuide = new Intent(this,RatingGuide.class);
//        itRatingGuide.putExtra("guideName","Ricardo");
//        startActivity(itRatingGuide);
//    }

    
}