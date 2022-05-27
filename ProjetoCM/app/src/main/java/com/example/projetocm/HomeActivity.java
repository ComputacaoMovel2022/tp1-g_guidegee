package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class HomeActivity extends AppCompatActivity {

    int[] profilePictures = {R.drawable.test_profile_pic, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon, R.drawable.empty_profile_icon};
    String[] profileNames = {"Mariana Gustavo", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio", "Perfil Vazio"};
    ListView simpleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void clickOnMessagesImage(View view) {
        Intent intent = new Intent(getApplicationContext(), MessageListPage.class);
        startActivity(intent);
    }
}