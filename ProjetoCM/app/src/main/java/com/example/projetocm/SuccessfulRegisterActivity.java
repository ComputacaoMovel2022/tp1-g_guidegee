package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SuccessfulRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.successful_register);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }

    public void successfulRegisterClick(View view)
    {
        startActivity(new Intent(SuccessfulRegisterActivity.this, LoginPageActivity.class));
        finish();
    }
}