package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }

    public void typeOfAccountClick(View view)
    {
        startActivity(new Intent(LoginPageActivity.this, AccountTypeActivity.class));
        finish();
    }

    public void loadingPageClick(View view)
    {
        startActivity(new Intent(LoginPageActivity.this, LoadingPageActivity.class));
        finish();
    }
}