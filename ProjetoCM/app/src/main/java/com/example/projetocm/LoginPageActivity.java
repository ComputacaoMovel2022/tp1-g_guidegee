package com.example.projetocm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        EditText usernameText = (EditText) findViewById(R.id.logemailtext);
        String username = usernameText.getText().toString();

        EditText passText = (EditText) findViewById(R.id.logpasstext);
        String pass = passText.getText().toString();

        mAuth.signInWithEmailAndPassword(username, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("GuideGee", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginPageActivity.this, LoadingPageActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("GuideGee", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginPageActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}