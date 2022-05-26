package com.example.projetocm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class GuideSignUpActivity extends AppCompatActivity {
    private DAOUser daoUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_sign_up);
        daoUser = new DAOUser();
        mAuth = FirebaseAuth.getInstance();

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }


    public void guideSignUpClick(View view)
    {
        EditText usernameText = (EditText) findViewById(R.id.gusernametext);
        String username = usernameText.getText().toString().trim();

        EditText emailText = (EditText) findViewById(R.id.gemailtext);
        String email = emailText.getText().toString().trim();

        EditText userid = (EditText) findViewById(R.id.gidtext);
        String id = userid.getText().toString().trim();

        EditText passText = (EditText) findViewById(R.id.gpasstext);
        String pass = passText.getText().toString().trim();

        EditText pass2Text = (EditText) findViewById(R.id.gpass2text);
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

        if (id.isEmpty()) {
            userid.setError("Este campo está vazio!");
            userid.requestFocus();
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
            pass2Text.setError("Password2 is required!");
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
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User registeredUser = new User(username, email, pass, id);

                            daoUser.set(mAuth.getCurrentUser().getUid(), registeredUser);
                            startActivity(new Intent(GuideSignUpActivity.this, SuccessfulRegisterActivity.class));
                            finish();
                        }
                    }
                });
    }
}
