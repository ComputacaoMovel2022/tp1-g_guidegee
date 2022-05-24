package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AccountTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_type);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }

    public void guideSignUpClick(View view)
    {
        startActivity(new Intent(AccountTypeActivity.this, GuideSignUpActivity.class));
        finish();
    }

    public void refugeeSignUpClick(View view)
    {
        startActivity(new Intent(AccountTypeActivity.this, RefugeeSignUpActivity.class));
        finish();
    }
}