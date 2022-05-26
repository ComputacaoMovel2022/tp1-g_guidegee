package com.example.projetocm;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DAOMessages {
    private DatabaseReference databaseReference;
    private String messageReceiverKey;
    private String messageSenderKey;
    private final String RECEIVER_ATTRIBUTE_NAME = "receiver";
    private final String SENDER_ATTRIBUTE_NAME = "sender";


    public DAOMessages(String messageSenderKey, String messageReceiverKey) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://guidegee-476d1-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = db.getReference("MessageChannels");
        this.messageReceiverKey = messageReceiverKey;
        this.messageSenderKey = messageSenderKey;
    }

    private DatabaseReference changeReferenceToChannel() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean wasReferenceChanged = false;
                for (DataSnapshot ds: snapshot.getChildren()) {
                    if (ds.child(RECEIVER_ATTRIBUTE_NAME).getValue(String.class).equals(messageReceiverKey) &&
                            ds.child(SENDER_ATTRIBUTE_NAME).getValue(String.class).equals(messageSenderKey)) {
                        databaseReference = databaseReference.child(ds.getKey());
                        wasReferenceChanged = true;
                    }
                }

                if (!wasReferenceChanged) {
                    String channelKey = databaseReference.push().getKey();
                    databaseReference.child(channelKey).child(RECEIVER_ATTRIBUTE_NAME).setValue(messageReceiverKey);
                    databaseReference.child(channelKey).child(SENDER_ATTRIBUTE_NAME).setValue(messageSenderKey);

                    databaseReference = databaseReference.child(channelKey);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String channelKey = databaseReference.push().getKey();
        databaseReference.child(channelKey).child(RECEIVER_ATTRIBUTE_NAME).setValue(messageReceiverKey);
        databaseReference.child(channelKey).child(SENDER_ATTRIBUTE_NAME).setValue(messageSenderKey);
        return databaseReference.child(channelKey);
    }

    public Task<Void> addMessage(String messageContent) {
        String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        return databaseReference.child("messages").push().setValue(new ChatMessage(messageContent, username));
    }


}
