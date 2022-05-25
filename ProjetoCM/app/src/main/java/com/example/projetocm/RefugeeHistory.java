package com.example.projetocm;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RefugeeHistory extends AppCompatActivity {
    private ListView listView;
    private List<User> allHistoryRefugees;
    private DAOUser daoUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.refugee_history);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }

        listView = findViewById(R.id.refugeeHistoryList);
        daoUser = new DAOUser();
        allHistoryRefugees = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        String loggedUserKey = null;
        if (extras != null) {
            loggedUserKey = extras.getString("loggedUserKey");
        }
        String finalLoggedUserKey = loggedUserKey;
        ImageView noHistoryImg = findViewById(R.id.empty_history_imageview);
        daoUser.getDataSnapshotWhenChanged(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User loggedUser = snapshot.child(finalLoggedUserKey).getValue(User.class);
                loggedUser.setUserKey(finalLoggedUserKey);

                if (snapshot.child("AllAssociatedUsers").exists()) {
                    noHistoryImg.setVisibility(View.INVISIBLE);
                    for (DataSnapshot ds: snapshot.child(loggedUser.getUserKey()).child("AllAssociatedUsers").getChildren()) {
                        String associatedUserKey = ds.getValue(String.class);

                        User foundUser = snapshot.child(associatedUserKey).getValue(User.class);
                        foundUser.setUserKey(associatedUserKey);
                        allHistoryRefugees.add(foundUser);
                    }
                } else {
                    noHistoryImg.setVisibility(View.VISIBLE);
                }


                SimplifiedProfileItem simplifiedProfileItem = new SimplifiedProfileItem(getApplicationContext(), R.layout.refugee_history_list_element, allHistoryRefugees);
                listView.setAdapter(simplifiedProfileItem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
