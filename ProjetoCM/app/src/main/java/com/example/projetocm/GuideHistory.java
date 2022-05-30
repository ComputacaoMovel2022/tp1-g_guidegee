package com.example.projetocm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GuideHistory extends AppCompatActivity {
    private ListView simpleList;
    private List<User> allHistoryGuides;
    private DAOUser daoUser;
    private Context guideHistoryContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_history);
        getSupportActionBar().hide();

        guideHistoryContext = this;

        //!-------------------------!//
        //!     Lista de Guias      !//
        //!-------------------------!//

        simpleList = findViewById(R.id.guidesListView);
        daoUser = new DAOUser();
        allHistoryGuides = new ArrayList<>();

        //get User logged key
        String loggedUserKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        /* When login is implemented
        *   FirebaseAuth.getInstance().getCurrentUser().getuid();
        */

        ImageView noHistoryImg = (ImageView) findViewById(R.id.empty_history_imageview);
        daoUser.getDataSnapshotWhenChanged(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User loggedUser = snapshot.child(loggedUserKey).getValue(User.class);
                loggedUser.setUserKey(loggedUserKey);

                if(snapshot.child(loggedUser.getUserKey()).child("AllAssociatedUsers").exists()){
                    noHistoryImg.setVisibility(View.INVISIBLE);
                    //gets a Iterable<DataSnapshot> com todos os filhos da snapshot
                    for(DataSnapshot data: snapshot.child(loggedUser.getUserKey()).child("AllAssociatedUsers").getChildren()){
                        String guideKey = data.getValue(String.class);

                        User guideUser = snapshot.child(guideKey).getValue(User.class);
                        guideUser.setUserKey(guideKey);
                        allHistoryGuides.add(guideUser);
                    }
                }else{
                    noHistoryImg.setVisibility(View.VISIBLE);
                }

                ListAdapterGuideHistory listAdapterGuideHistoryGuide = new ListAdapterGuideHistory(getApplicationContext(), R.layout.guide_history_list_element, allHistoryGuides);
                listAdapterGuideHistoryGuide.sendContext(guideHistoryContext);
                simpleList.setAdapter(listAdapterGuideHistoryGuide);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        View goBackButton = (View) findViewById(R.id.arrow_2);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to Home_page
                startActivity(new Intent(GuideHistory.this,HomeActivity.class));
                finish();
            }
        });
    }

}