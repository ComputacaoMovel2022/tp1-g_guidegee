package com.example.projetocm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        mAuth = FirebaseAuth.getInstance();

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }

    public void typeOfAccountClick(View view)
    {
        startActivity(new Intent(LoginPageActivity.this, AccountTypeActivity.class));
        finish();
    }

    public void loadingPageClick(View view)
    {
        EditText emailText = (EditText) findViewById(R.id.logemailtext);
        String email = emailText.getText().toString().trim();

        EditText passwordText = (EditText) findViewById(R.id.logpasstext);
        String password = passwordText.getText().toString().trim();

        if (email.isEmpty()) {
            emailText.setError("Username is required!");
            emailText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Please enter a valid email!");
            emailText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordText.setError("Email is required!");
            passwordText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordText.setError("Password must be 6 or more characters!");
            passwordText.requestFocus();
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginPageActivity.this, LoadingPageActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed! Check credentials, please.", Toast.LENGTH_SHORT).show();
                        }
                        mAuth.getCurrentUser().getUid()
                    }
                });

    }
}