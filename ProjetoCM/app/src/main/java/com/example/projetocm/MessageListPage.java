package com.example.projetocm;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class MessageListPage extends AppCompatActivity {
    private DAOUser daoUser;
    private ListView listView;
    private List<User> allUsers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.message_list);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }

        listView = findViewById(R.id.messageListView);
        daoUser = new DAOUser();
        allUsers = new ArrayList<>();

        String finalLoggedUserKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        daoUser.getDataSnapshotWhenChanged(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User loggedUser = snapshot.child(finalLoggedUserKey).getValue(User.class);
                loggedUser.setUserKey(finalLoggedUserKey);


                //if (snapshot.child("AllAssociatedUsers").exists()) {

                    for (DataSnapshot ds: snapshot.child(loggedUser.getUserKey()).child("AllAssociatedUsers").getChildren()) {
                        System.out.println("INSIDE FOR");
                        String associatedUserKey = ds.getValue(String.class);

                        User foundUser = snapshot.child(associatedUserKey).getValue(User.class);
                        foundUser.setUserKey(associatedUserKey);
                        allUsers.add(foundUser);
                    }
                //}

                MessageListItemAdapter messageListItemAdapter = new MessageListItemAdapter(getBaseContext(), R.layout.message_list_item, allUsers);
                listView.setAdapter(messageListItemAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
