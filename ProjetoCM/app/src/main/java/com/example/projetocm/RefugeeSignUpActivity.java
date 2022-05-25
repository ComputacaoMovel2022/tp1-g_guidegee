package com.example.projetocm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaDataSource;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RefugeeSignUpActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refugee_sign_up);
        mDatabase = FirebaseDatabase.getInstance("https://guidegee-476d1-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        mAuth = FirebaseAuth.getInstance();

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }

    public void refugeeSignUpClick(View view)
    {
        EditText usernameText = (EditText) findViewById(R.id.rusernametext);
        String username = usernameText.getText().toString();

        EditText emailText = (EditText) findViewById(R.id.remailtext);
        String email = emailText.getText().toString();

        EditText passText = (EditText) findViewById(R.id.rpasstext);
        String pass = passText.getText().toString();

        EditText pass2Text = (EditText) findViewById(R.id.rpass2text);
        String pass2 = pass2Text.getText().toString();

        if(pass.equals(pass2))
        {
            User u = new User(username, email, pass);
            mDatabase.child("User").child(u.getUserID()).setValue(u);

            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("GuideGee", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Guidegee", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RefugeeSignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                reload();
                            }
                        }
                    });

            startActivity(new Intent(RefugeeSignUpActivity.this, SuccessfulRegisterActivity.class));
            finish();
        }
    }

    private void reload(){}
}