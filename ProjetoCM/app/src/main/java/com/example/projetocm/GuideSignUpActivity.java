package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GuideSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_sign_up);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }


    public void refugeeSignUpClick(View view)
    {
        startActivity(new Intent(GuideSignUpActivity.this, SuccessfulRegisterActivity.class));
        finish();
    }

}