package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GuideSignUpActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_sign_up);
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
            mDatabase.child("User").child(u.getUserID()).setValue(u);

            startActivity(new Intent(GuideSignUpActivity.this, SuccessfulRegisterActivity.class));
            finish();
        }
    }

}