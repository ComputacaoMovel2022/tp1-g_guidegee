package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    public void loadingPageClick(View view)
    {
        startActivity(new Intent(GuestLoginActivity.this, LoadingPageActivity.class));
        finish();
    }

}