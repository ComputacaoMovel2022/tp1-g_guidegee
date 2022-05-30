package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class StartPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);

        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_ProjetoCM);
        setContentView(R.layout.start_page);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("rememberMe", "");
        if (FirebaseAuth.getInstance().getCurrentUser() != null && checkbox.equals("true")) {
            startActivity(new Intent(StartPageActivity.this, LoadingPageActivity.class));
            finish();
        }
    }

    public void guestLoginClick(View view)
    {
        startActivity(new Intent(StartPageActivity.this, GuestLoginActivity.class));
        finish();
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