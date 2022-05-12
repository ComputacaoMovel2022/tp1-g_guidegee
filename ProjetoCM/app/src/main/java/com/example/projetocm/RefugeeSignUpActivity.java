package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaDataSource;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RefugeeSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refugee_sign_up);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }

    public void refugeeSignUpClick(View view)
    {
        startActivity(new Intent(RefugeeSignUpActivity.this, SuccessfulRegisterActivity.class));
        finish();
    }
}