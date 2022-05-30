package com.example.projetocm;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestPage extends AppCompatActivity {
    private ListView simpleList;
    private List<User> allRequests;
    private DAOUser daoUser;
    private Context requestPageContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requests_from_refugees);
        getSupportActionBar().hide();

        requestPageContext = this;

        simpleList = findViewById(R.id.list_requests_from_refugees);
        daoUser = new DAOUser();
        allRequests = new ArrayList<>();

        String loggedUserKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        daoUser.getDataSnapshotWhenChanged(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> allRequestsKeys = new ArrayList<>();
                for(DataSnapshot data: snapshot.child(loggedUserKey).child("AllRequestsUsers").getChildren()){
                    allRequestsKeys.add(data.getValue(String.class));
                }
                for(String requestKey:allRequestsKeys){
                    User curUser = snapshot.child(requestKey).getValue(User.class);
                    curUser.setUserKey(requestKey);

                    allRequests.add(curUser);
                }

                ListAdapterRequestPage listAdapterRequestPage = new ListAdapterRequestPage(getApplicationContext(), R.layout.requests_from_refugees_list_element, allRequests,loggedUserKey);
                listAdapterRequestPage.sendContext(requestPageContext);
                simpleList.setAdapter(listAdapterRequestPage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
