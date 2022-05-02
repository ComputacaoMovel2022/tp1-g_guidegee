package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

        Button signUpButton = (Button) findViewById(R.id.signupbtn);
    }

    public void typeOfAccountClick(View view)
    {
        startActivity(new Intent(StartPageActivity.this, AccountTypeActivity.class));
        finish();
    }

    public void loginClick(View view)
    {
        startActivity(new Intent(StartPageActivity.this, LoginPageActivity.class));
        finish();
    }
}