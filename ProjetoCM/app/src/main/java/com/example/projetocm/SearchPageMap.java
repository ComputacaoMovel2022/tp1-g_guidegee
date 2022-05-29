package com.example.projetocm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchPageMap extends AppCompatActivity{

    private ListView simpleList;
    private List<User> allAvailableGuides;
    private DAOUser daoUser;
    private Context searchPageMapContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page_map);
        getSupportActionBar().hide();

        searchPageMapContext = this;


        simpleList = findViewById(R.id.guidesListView);
        daoUser = new DAOUser();
        allAvailableGuides = new ArrayList<>();

        String loggedUserKey = FirebaseAuth.getInstance().getCurrentUser().getUid();


        ImageView noHistoryImg = (ImageView) findViewById(R.id.empty_history_imageview);
        daoUser.getDataSnapshotOnce(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    if(data.child("userGuide").getValue(Boolean.class)){
                        if(data.child("isAvailable").exists()) {
                            if (data.child("isAvailable").getValue(Boolean.class)) {
                                boolean isAlreadyRequested = false;
                                for (DataSnapshot dataRequests : data.child("AllRequestsUsers").getChildren()) {
                                    if (dataRequests.getValue(String.class).equalsIgnoreCase(loggedUserKey)) {
                                        isAlreadyRequested = true;
                                    }
                                }
                                if (!isAlreadyRequested) {
                                    User curUser = data.getValue(User.class);
                                    curUser.setUserKey(data.getKey());
                                    allAvailableGuides.add(curUser);
                                }
                            }
                        }
                    }
                }
                ListAdapterSearchPageMap listAdapterSearchPageMap = new ListAdapterSearchPageMap(getApplicationContext(), R.layout.search_page_map_list_element, allAvailableGuides, loggedUserKey);
                listAdapterSearchPageMap.sendContext(searchPageMapContext);
                simpleList.setAdapter(listAdapterSearchPageMap);

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
                startActivity(new Intent(SearchPageMap.this,HomeActivity.class));
                finish();
            }
        });
    }
}
