package com.example.projetocm;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DAOMessages {
    private DatabaseReference databaseReference;
    private String messageReceiverKey;
    private String messageSenderKey;
    private final String USER1_ATTRIBUTE_NAME = "user1";
    private final String USER2_ATTRIBUTE_NAME = "user2";


    public DAOMessages(String messageSenderKey, String messageReceiverKey) {
        System.out.println("INSIDE DAO CONSTRUCTOR");
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://guidegee-476d1-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = db.getReference("MessageChannels");
        this.messageReceiverKey = messageReceiverKey;
        this.messageSenderKey = messageSenderKey;
        System.out.println("BEFORE CHANGING REFERENCE: " + databaseReference.getKey());
        changeReferenceToChannel();
        System.out.println("AFTER CHANGING REFERENCE" + databaseReference.getKey());
    }

    private void changeReferenceToChannel() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean wasReferenceChanged = false;
                for (DataSnapshot ds: snapshot.getChildren()) {
                    if (usersExistsInDatabase(ds)) {
                        System.out.println(ds.getKey());
                        databaseReference = databaseReference.child(ds.getKey());
                        wasReferenceChanged = true;
                    }
                }

                if (!wasReferenceChanged) {
                    String channelKey = databaseReference.push().getKey();
                    databaseReference.child(channelKey).child(USER1_ATTRIBUTE_NAME).setValue(messageReceiverKey);
                    databaseReference.child(channelKey).child(USER2_ATTRIBUTE_NAME).setValue(messageSenderKey);

                    databaseReference = databaseReference.child(channelKey);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public Task<Void> addMessage(ChatMessage message) {
        return databaseReference.child("messages").push().setValue(message);
    }

    private boolean usersExistsInDatabase(DataSnapshot ds) {
        String user1Key = ds.child(USER1_ATTRIBUTE_NAME).getValue(String.class);
        String user2Key = ds.child(USER2_ATTRIBUTE_NAME).getValue(String.class);
        return (user1Key.equals(messageReceiverKey) ||
                user1Key.equals(messageSenderKey)) &&
                (user2Key.equals(messageReceiverKey) ||
                 user2Key.equals(messageSenderKey));
    }

    public void keepListUpdated(List<ChatMessage> allMessages, ListAdapterMessagePage adapter) {
        System.out.println("KEEP LIST UPDATED: " + databaseReference.getKey());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //System.out.println(snapshot.getKey());
                allMessages.clear();
                for (DataSnapshot ds: snapshot.child("messages").getChildren()) {
                    allMessages.add(ds.getValue(ChatMessage.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
