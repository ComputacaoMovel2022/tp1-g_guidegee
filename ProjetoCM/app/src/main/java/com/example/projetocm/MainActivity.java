package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    int[] profilePictures = {R.drawable.test_profile_pic, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon};
    String[] profileNames = {"Mariana Gustavo", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio"};
    ListView simpleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refugee_history);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }

        simpleList = findViewById(R.id.refugeeHistoryList);
        System.out.println("GOT HERE");
        SimplifiedProfileItem simplifiedProfileItem = new SimplifiedProfileItem(getApplicationContext(), R.layout.refugee_history_list_element, profileNames, profilePictures);
        simpleList.setAdapter(simplifiedProfileItem);

    }
}