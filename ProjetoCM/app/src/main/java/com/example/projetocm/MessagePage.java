package com.example.projetocm;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MessagePage extends AppCompatActivity {
    int[] profilePictures = {R.drawable.ellipse_20, R.drawable.empty_profile_icon};
    String[] messages = {"Texto de exemplo relativamente muito longo para dar um exemplo com 2 linhas lol", "Text Vazio"};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_page);
        getSupportActionBar().hide();

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
                String message = textBox.getText().toString();
                //Send Message
                textBox.setText("");

            }
        });


        //Lista de Mensagens
        listView = (ListView) findViewById(R.id.messageListView);
        ListAdapterMessagePage listAdapter = new ListAdapterMessagePage(getApplicationContext(),this.messages,this.profilePictures,R.layout.message_bubble_element);
        listView.setAdapter(listAdapter);
    }
}
