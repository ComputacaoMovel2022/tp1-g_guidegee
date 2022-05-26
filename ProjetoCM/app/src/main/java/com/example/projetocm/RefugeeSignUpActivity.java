package com.example.projetocm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaDataSource;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
*/

public class RefugeeSignUpActivity extends AppCompatActivity {
    private DAOUser daoUser;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refugee_sign_up);
        daoUser = new DAOUser();
        mAuth = FirebaseAuth.getInstance();

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }

    public void refugeeSignUpClick(View view)
    {
        EditText usernameText = (EditText) findViewById(R.id.rusernametext);
        String username = usernameText.getText().toString().trim();

        EditText emailText = (EditText) findViewById(R.id.remailtext);
        String email = emailText.getText().toString().trim();

        EditText passText = (EditText) findViewById(R.id.rpasstext);
        String pass = passText.getText().toString().trim();

        EditText pass2Text = (EditText) findViewById(R.id.rpass2text);
        String pass2 = pass2Text.getText().toString().trim();

        if (username.isEmpty()) {
            usernameText.setError("Username is required!");
            usernameText.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            emailText.setError("Email is required!");
            emailText.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            passText.setError("Password is required!");
            passText.requestFocus();
            return;
        }

        if (pass.length() < 6) {
            passText.setError("Password must be 6 or more characters!");
            passText.requestFocus();
        }

        if (pass2.isEmpty()) {
            pass2Text.setError("Username is required!");
            pass2Text.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Providencie um mail válido, por favor!");
            emailText.requestFocus();
            return;
        }

        if (!pass.equals(pass2)) {
            pass2Text.setError("Not the same password as the other one!");
            pass2Text.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User registeredUser = new User(username, email, pass);

                            daoUser.set(mAuth.getCurrentUser().getUid(), registeredUser);
                            startActivity(new Intent(RefugeeSignUpActivity.this, SuccessfulRegisterActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed authentication", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}