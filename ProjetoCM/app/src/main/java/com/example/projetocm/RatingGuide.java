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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RatingGuide extends AppCompatActivity {
    String guideKey;
    String refugeeKey;
    double previousRating;
    String allReviewedUserId;

    DAOUser daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_guide);
        getSupportActionBar().hide();

            Intent it = getIntent();
            guideKey = it.getStringExtra("guideKey");

            refugeeKey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        daoUser= new DAOUser();


        // initiate rating bar and a button
        final RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar);
        Button submitButton = (Button) findViewById(R.id.submitButton);
        Button backButton = (Button) findViewById(R.id.buttonBack);
        // perform click event on button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double ratingValue = (double) simpleRatingBar.getRating();
                previousRating = (double) 0;

                daoUser.getDataSnapshotOnce(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User guideUser = snapshot.child(guideKey).getValue(User.class);
                        guideUser.setUserKey(guideKey);
                        boolean foundUser = false;

                        for (DataSnapshot data : snapshot.child(refugeeKey).child("AllReviewedUsers").getChildren()) {
                            String guideKeyDB = data.child("userKey").getValue(String.class);
                            System.err.println("FoundUser:"+guideKeyDB);
                            if(guideKeyDB.equalsIgnoreCase(guideKey)){ //
                                previousRating = data.child("reviewedVal").getValue(Double.class);
                                foundUser = true;
                                System.err.println("First IF");
                                System.err.println("FoundUser:"+foundUser);
                                allReviewedUserId = data.getKey();
                            }
                        }


                        /* Equação */

                        int numOfReview = guideUser.getNumOfPplHelped();
                        double reviewVal = guideUser.getRatingScore();
                        double finalReview = 0.0;

                        /* Atribuição de Valores */
                        if(foundUser){
                            /* Alterar uma Review já dada */
                            daoUser.setUserReviewValue(refugeeKey,allReviewedUserId,ratingValue);
                            System.err.println("Second IF");
                        }else{
                            /* Fazer uma nova Review */
                            AllReviewedUsers allReviewedUsers = new AllReviewedUsers(guideKey,ratingValue);
                            daoUser.addReviewToList(refugeeKey,allReviewedUsers);
                            numOfReview++;
                        }

                        if(numOfReview==0){
                            finalReview=ratingValue;
                        }else{
                            finalReview=(((reviewVal-previousRating)/numOfReview)+ratingValue)/numOfReview;
                        }

                        System.err.println(finalReview);
                        daoUser.setUserAttributeValue(guideKey,"ratingScore",finalReview);
                        daoUser.setUserAttributeValue(guideKey,"numOfPplHelped",numOfReview);

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

                startActivity(new Intent(RatingGuide.this,GuideHistory.class));
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RatingGuide.this,GuideHistory.class));
                finish();
            }
        });


    }

    //getRating()
    //getNumStars()


}