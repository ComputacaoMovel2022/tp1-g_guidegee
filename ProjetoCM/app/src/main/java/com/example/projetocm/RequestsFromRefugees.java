package com.example.projetocm;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class RequestsFromRefugees extends AppCompatActivity {
    ListView listOfRefugees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requests_from_refugees);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        listOfRefugees = findViewById(R.id.list_requests_from_refugees);
        RequestFromRefugeeAdapter requestFromRefugeeAdapter = new RequestFromRefugeeAdapter();
    }

}
