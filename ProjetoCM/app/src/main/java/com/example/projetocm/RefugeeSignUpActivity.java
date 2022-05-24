package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaDataSource;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
*/

public class RefugeeSignUpActivity extends AppCompatActivity {
    private DAOUser daoUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refugee_sign_up);
        daoUser = new DAOUser();

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }

    public void refugeeSignUpClick(View view)
    {
        EditText usernameText = (EditText) findViewById(R.id.rusernametext);
        String username = usernameText.getText().toString();

        EditText emailText = (EditText) findViewById(R.id.remailtext);
        String email = emailText.getText().toString();

        EditText passText = (EditText) findViewById(R.id.rpasstext);
        String pass = passText.getText().toString();

        EditText pass2Text = (EditText) findViewById(R.id.rpass2text);
        String pass2 = pass2Text.getText().toString();

        if(pass.equals(pass2))
        {
            User u = new User(username, email, pass);
            //mDatabase.child("User").child(u.getUserID()).setValue(u);

            daoUser.add(u);

            startActivity(new Intent(RefugeeSignUpActivity.this, SuccessfulRegisterActivity.class));
            finish();
        }
    }
}