package com.example.projetocm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class GuideSignUpActivity extends AppCompatActivity {
    private DAOUser daoUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_sign_up);
        daoUser = new DAOUser();

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }


    public void guideSignUpClick(View view)
    {
        EditText usernameText = (EditText) findViewById(R.id.gusernametext);
        String username = usernameText.getText().toString();

        EditText emailText = (EditText) findViewById(R.id.gemailtext);
        String email = emailText.getText().toString();

        EditText userid = (EditText) findViewById(R.id.gidtext);
        String id = userid.getText().toString();

        EditText passText = (EditText) findViewById(R.id.gpasstext);
        String pass = passText.getText().toString();

        EditText pass2Text = (EditText) findViewById(R.id.gpass2text);
        String pass2 = pass2Text.getText().toString();

        if(pass.equals(pass2))
        {
            User u = new User(username, email, pass, id);
            //mDatabase.child("User").child(u.getUserID()).setValue(u);
            String key = daoUser.generateNewKey();
            daoUser.set(key, u);
            daoUser.addUserToList(key, "Item 1");
            daoUser.addUserToList(key, "Item 2");
            daoUser.addUserToList(key, "Item 3");

            daoUser.getDataSnapshotOnce(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child(key).exists()) {
                        System.out.println("FOUND USER!!! key: " + key);
                        daoUser.addUserToList(key, "Item 74");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.err.println(error.getMessage());
                }
            });

            startActivity(new Intent(GuideSignUpActivity.this, SuccessfulRegisterActivity.class));
            finish();
        }
    }

}