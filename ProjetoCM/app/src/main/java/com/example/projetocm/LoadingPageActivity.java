package com.example.projetocm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.util.Log;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LoadingPageActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_page);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }

        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setProgress(i);
        mCountDownTimer=new CountDownTimer(5000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i + millisUntilFinished);
                i++;
                mProgressBar.setProgress((int)i * 100 / (5000 / 1000));

            }

            @Override
            public void onFinish() {
                i++;
                mProgressBar.setProgress(100);
                startActivity(new Intent(LoadingPageActivity.this, HomeActivity.class));
                finish();
            }
        };
        mCountDownTimer.start();
        SharedPreferences preferences = getSharedPreferences("userDefinitions", MODE_PRIVATE);
        DAOUser daoUser = new DAOUser();
        daoUser.getDataSnapshotOnce(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class).isUserGuide()) {
                    preferences.edit().putString("isGuide", "true").apply();
                } else {
                    preferences.edit().putString("isGuide", "false").apply();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}