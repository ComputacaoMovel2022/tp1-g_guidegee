package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GuideHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_history);
        getSupportActionBar().hide();
    }
}