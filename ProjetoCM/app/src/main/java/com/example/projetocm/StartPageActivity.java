package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);

        //apenas para teste pois a app inicia demasiado rapido
        //TODO: mudar a cor do background da splashscreen para branco (ta preto em light mode)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_ProjetoCM);
        setContentView(R.layout.start_page);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }

    public void guestLoginClick(View view)
    {
        startActivity(new Intent(StartPageActivity.this, GuestLoginActivity.class));
        finish();
    }

    public void typeOfAccountClick(View view)
    {
        startActivity(new Intent(StartPageActivity.this, AccountTypeActivity.class));
        finish();
    }

    public void loginClick(View view)
    {
        startActivity(new Intent(StartPageActivity.this, LoginPageActivity.class));
        finish();
    }
}