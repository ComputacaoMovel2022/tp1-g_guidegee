package com.example.projetocm;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfile extends AppCompatActivity {
    public static final int Camera_Action_CODE=1;
    TextView textView;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Portuguese", "French", "Spanish", "Russian", "English", "Mandarim", "Hindi"};
    ActivityResultLauncher<Intent> activityResultLauncher;
    Uri file;
    StorageReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Compare Token
        //if equal add edit page
        //else if
        setContentView(R.layout.activity_edit_profile);
        DAOUser daoUser= new DAOUser();
        CircleImageView userImage = (CircleImageView)findViewById(R.id.UserImage);
        Picasso.get().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(userImage);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK && result.getData() != null){
                    Bundle bundle = result.getData().getExtras();
                    file = result.getData().getData();
                    ref = storageRef.child("images/"+file.getLastPathSegment());
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    userImage.setImageBitmap(bitmap);
                }
            }
        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra("return-data", false);
                    if(intent.resolveActivity(getPackageManager()) != null){
                        activityResultLauncher.launch(intent);
                    }
                }
            }
        });
        Button saveChanges= (Button)findViewById(R.id.SaveEdits);
        saveChanges.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Save
                if(file != null) {
                    UploadTask uploadTask = ref.putFile(file);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(getApplicationContext(), "Unable to change photo", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(),"imageURL",ref.getDownloadUrl());
                        }
                    });
                }
                EditText personName =  (EditText) findViewById(R.id.editTextTextPersonName);
                if(personName.getText().toString() != "" && personName.getText().toString() != null){
                    daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(),"username",personName.getText().toString());
                }
                EditText password =  (EditText) findViewById(R.id.editTextTextPassword);
                EditText passwordConfirm =  (EditText) findViewById(R.id.editTextTextPassword2);
                if(passwordConfirm.getText().toString() != "" && passwordConfirm.getText().toString() != null && password.getText().toString() == passwordConfirm.getText().toString()){
                    daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(),"password",passwordConfirm.getText().toString());
                }
                TextView description = (TextView) findViewById(R.id.User_description);
                if(description.getText().toString() != "" && description.getText().toString() != null){
                    //send description to DB
                    daoUser.setUserAttributeValue(FirebaseAuth.getInstance().getCurrentUser().getUid(),"description",personName.getText().toString());
                }
                Toast.makeText(getApplicationContext(), "Saves have been made", Toast.LENGTH_SHORT).show();
            }
        });
        Button cancelChanges= (Button)findViewById(R.id.CancelEdits);
        cancelChanges.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
                //Cancel
            }
        });
        textView = findViewById(R.id.spinner2);
        selectedLanguage = new boolean[langArray.length];

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);

                // set title
                builder.setTitle("Select Language");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);


                            // add in DataBase the lang option


                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));


                            // Remove lang from the langs know

                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(langArray[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        textView.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            textView.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},200);
    }
}