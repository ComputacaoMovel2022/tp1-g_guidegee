package com.example.projetocm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    DAOUser daoUser = new DAOUser();
    private List<User> topGuides;
    private ListView simpleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_home);

        simpleList = findViewById(R.id.topGuidesListView);
        topGuides = new ArrayList<>();
        daoUser.getDataSnapshotOnce(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                double rankingValues[] = new double[3];
                for(int i=0;i<3;i++) {
                    User curBestGuide = null;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        if(data.child("userGuide").getValue(Boolean.class)){
                            double curUserRating =data.child("ratingScore").getValue(Double.class);
                            double curUserNumOfPplHelped = data.child("numOfPplHelped").getValue(Double.class);
                            double curRankingValue = curUserRating+curUserNumOfPplHelped*2;

                            if(rankingValues[i]<curRankingValue){
                                if(i!=0) {
                                    if(!(curRankingValue>=rankingValues[i-1])){
                                        rankingValues[i] = curRankingValue;
                                        curBestGuide = data.getValue(User.class);
                                        curBestGuide.setUserKey(data.getKey());
                                    }
                                }else{
                                    rankingValues[i] = curRankingValue;
                                    curBestGuide = data.getValue(User.class);
                                    curBestGuide.setUserKey(data.getKey());
                                }
                            }
                        }
                    }
                    topGuides.add(curBestGuide);
                    curBestGuide = null;
                }

                List<User> removeList = new ArrayList<>();
                for(User guide: topGuides){
                    if(guide==null){
                        removeList.add(guide);
                    }
                }
                topGuides.removeAll(removeList);

                ListAdapterBestGuide listAdapterBestGuide = new ListAdapterBestGuide(getApplicationContext(), R.layout.best_guide_list_element, topGuides);
                simpleList.setAdapter(listAdapterBestGuide);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void clickProfile(View view) {
        startActivity(new Intent(HomeActivity.this, EditProfile.class));
        finish();
    }
    public void clickOnMessagesImage(View view) {
        Intent intent = new Intent(getApplicationContext(), MessageListPage.class);
        startActivity(intent);
    }
}