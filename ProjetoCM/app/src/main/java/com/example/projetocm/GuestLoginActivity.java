package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GuestLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_login);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }



}