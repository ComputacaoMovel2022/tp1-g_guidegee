package com.example.projetocm;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessagePageActivity extends AppCompatActivity {
    //int[] profilePictures = {R.drawable.ellipse_20, R.drawable.empty_profile_icon};
    private List<ChatMessage> messages;
    private ListView listView;
    private DAOMessages daoMessages;
    private DAOUser daoUser;
    private String imageURLSender;
    private String imageURLReceiver;
    private ListAdapterMessagePage listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_page);
        getSupportActionBar().hide();

        String messageReceiverUserKey = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            messageReceiverUserKey = extras.getString("messageReceiver");
        }
        String finalMessageReceiverUserKey = messageReceiverUserKey;

        messages = new ArrayList<>();
        daoUser = new DAOUser();
        daoMessages = new DAOMessages(FirebaseAuth.getInstance().getCurrentUser().getUid(), messageReceiverUserKey);
        listView = (ListView) findViewById(R.id.messagePageListView);
        EditText messageEditText = findViewById(R.id.messageBox);
        //messageEditText.requestFocus();

        View goBackButton = (View) findViewById(R.id.arrow_2);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageView sendMessageButton =(ImageView) findViewById(R.id.messageSendButton);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textBox = (EditText) findViewById(R.id.messageBox);
                String messageContent = textBox.getText().toString().trim();
                if (messageContent.isEmpty()) {
                    return;
                }
                //Send Message
                textBox.setText("");
                ChatMessage message = new ChatMessage(messageContent, FirebaseAuth.getInstance().getCurrentUser().getUid());
                daoMessages.addMessage(message);
            }
        });

        ProgressBar progressBar = findViewById(R.id.progressBarMessage);
        daoUser.getDataSnapshotOnce(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView otherUsernameText = findViewById(R.id.messagePageOtherUsername);
                otherUsernameText.setText(snapshot.child(finalMessageReceiverUserKey).child("username").getValue(String.class));

                progressBar.setVisibility(View.VISIBLE);
                imageURLSender = snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imageURL").getValue(String.class);
                imageURLReceiver = snapshot.child(finalMessageReceiverUserKey).child("imageURL").getValue(String.class);

                listAdapter = new ListAdapterMessagePage(getBaseContext(),
                        messages,
                        R.layout.message_bubble_element_sender,
                        R.layout.message_bubble_element_receiver,
                        imageURLSender, imageURLReceiver
                );
                listView.setAdapter(listAdapter);

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        daoMessages.keepListUpdated(messages, listAdapter);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                };

                Handler h = new Handler();
                h.postDelayed(r, 2000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
