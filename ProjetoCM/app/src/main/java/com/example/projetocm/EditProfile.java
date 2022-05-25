package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Compare Token
        //if equal add edit page
        //else if
        setContentView(R.layout.activity_edit_profile);
        Button saveChanges= (Button)findViewById(R.id.SaveEdits);
        saveChanges.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Save
            }
        });
        Button cancelChanges= (Button)findViewById(R.id.CancelEdits);
        cancelChanges.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Cancel
            }
        });
    }
}