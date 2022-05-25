package com.example.projetocm;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestsFromRefugees extends AppCompatActivity {
    private ListView listOfRefugees;
    private DAOUser daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requests_from_refugees);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        daoUser = new DAOUser();

        listOfRefugees = findViewById(R.id.list_requests_from_refugees);
        Bundle extras = getIntent().getExtras();
        String loggedUserKey = null;
        if (extras != null) {
            loggedUserKey = extras.getString("loggedUserKey");
        }
        String finalLoggedUserKey = loggedUserKey;
        daoUser.getDataSnapshotWhenChanged(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> userRequests = new ArrayList<>();

                User loggedUser = snapshot.child(finalLoggedUserKey).getValue(User.class);
                loggedUser.setUserKey(finalLoggedUserKey);

                for (DataSnapshot ds: snapshot.child(loggedUser.getUserKey()).child("allRequests").getChildren()) {
                    if (ds.exists()) {
                        String userKey = ds.getValue(String.class);
                        User foundUser = snapshot.child(userKey).getValue(User.class);
                        foundUser.setUserKey(userKey);
                        userRequests.add(foundUser);
                    }
                }
                RequestFromRefugeeAdapter requestFromRefugeeAdapter = new RequestFromRefugeeAdapter(
                        getApplicationContext(),
                        R.layout.requests_from_refugees_list_element,
                        userRequests, finalLoggedUserKey
                );
                listOfRefugees.setAdapter(requestFromRefugeeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
