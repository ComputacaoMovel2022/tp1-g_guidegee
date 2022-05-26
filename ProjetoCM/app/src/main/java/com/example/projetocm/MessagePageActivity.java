package com.example.projetocm;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class MessagePageActivity extends AppCompatActivity {
    int[] profilePictures = {R.drawable.ellipse_20, R.drawable.empty_profile_icon};
    String[] messages = {"Texto de exemplo relativamente muito longo para dar um exemplo com 2 linhas lol", "Text Vazio"};
    private ListView listView;
    private DAOMessages daoMessages;

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

        daoMessages = new DAOMessages(FirebaseAuth.getInstance().getCurrentUser().getUid(), messageReceiverUserKey);

        //    Intent it = getIntent();
        //    recipient = it.getStringExtra("user");

        View goBackButton = (View) findViewById(R.id.arrow_2);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go back to (List of Messages)
            }
        });

        ImageView sendMessageButton =(ImageView) findViewById(R.id.messageSendButton);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textBox = (EditText) findViewById(R.id.messageBox);
                String message = textBox.getText().toString().trim();
                if (message.isEmpty()) {
                    return;
                }
                //Send Message
                textBox.setText("");
                daoMessages.addMessage(message);
            }
        });


        //Lista de Mensagens

        ListAdapterMessagePage listAdapter = new ListAdapterMessagePage(getApplicationContext(),this.messages,this.profilePictures,R.layout.message_bubble_element_receiver);
        listView.setAdapter(listAdapter);
    }

    private void loadChatMessages() {
        listView = (ListView) findViewById(R.id.messageListView);
        FirebaseListAdapter<ChatMessage> adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class, R) {
            @Override
            protected void populateView(View view, ChatMessage model, int position) {
                EditText messageContent = view.findViewById(R.id.messageBox);
            }
        }
    }
}
