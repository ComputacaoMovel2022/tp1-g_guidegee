package com.example.projetocm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class RatingGuide extends AppCompatActivity {
    //RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar);
    //Button submitButton = (Button) findViewById(R.id.submitButton);
    String guideKey;

    DAOUser daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_guide);
        getSupportActionBar().hide();

            Intent it = getIntent();
            guideKey = it.getStringExtra("guideKey");

        daoUser= new DAOUser();


        // initiate rating bar and a button
        final RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar);
        Button submitButton = (Button) findViewById(R.id.submitButton);
        Button backButton = (Button) findViewById(R.id.buttonBack);
        // perform click event on button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float ratingValue = simpleRatingBar.getRating();


                daoUser.getDataSnapshotOnce(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        /* ERRO -> UM UTILIZADOR PODE FAZER MILHOES DE REVIEWS DIFERENTES */
                        User loggedUser = snapshot.child(guideKey).getValue(User.class);
                        loggedUser.setUserKey(guideKey);


                        // NUMOFPpleHelped ESTÁ ERRADO a varialvel tem o nome errado
                        int numOfReview = loggedUser.getNumOfPplHelped();
                        double reviewVal = loggedUser.getRatingScore();


                        double finalReview = ((reviewVal/numOfReview)+ratingValue)/numOfReview+1;

                        daoUser.setUserAttributeValue(guideKey,"ratingScore",finalReview);
                        daoUser.setUserAttributeValue(guideKey,"numOfPplHelped",numOfReview+1);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

               /*
                * Toast.makeText(getApplicationContext(),
                *         "Nome do Guia:"+guideName,
                *         Toast.LENGTH_LONG).show();
                */
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent guideHistory = new Intent(RatingGuide.this,MainActivity.class);
                startActivity(guideHistory);*/
            }
        });


    }

    //getRating()
    //getNumStars()


}