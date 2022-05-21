package com.example.projetocm;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Database abstraction in operations related to users.
 * Use this object instead of making hard-to-read database calls to the User table.
 */
public class DAOUser {
    private DatabaseReference databaseReference;
    private final String ALL_ASSOCIATED_USERS_ATTRIBUTE = "AllAssociatedUsers";

    public DAOUser() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://guidegee-476d1-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = db.getReference(User.class.getSimpleName());

    }

    public Task<Void> add(User user) {
        return databaseReference.push().setValue(user);
    }

    public Task<Void> set(String key, User user) {
        return databaseReference.child(key).setValue(user);
    }

    public Task<Void> removeUser(String key) {
        return databaseReference.child(key).removeValue();
    }

    /**
     * Returns a SINGLE user based on a key.
     * Do not use this method multiple times in a row, like in a for loop.
     * Doing this may cause performance issues. Instead, use a real time listener.
     *
     * @param key The key of the user you want to search for in the database.
     * @param onCompleteListener The listener which will execute when it finishes the search.
     */
    public void getUser(String key, OnCompleteListener<DataSnapshot> onCompleteListener) {
        databaseReference.child(key).get().addOnCompleteListener(onCompleteListener);
    }

    /**
     * Adds an user to a user list which the main user is associated.
     * Solves the situation of the guides being associated to refugees, and the refugees
     * being associated to guides.
     * You simply put the key of the other in a list of users.
     *
     * @param mainUserKey Main user whose list will get a new key.
     * @param associatedUserKey User that will be added to the mainUser's user list.
     * @return a task, which can be used for error checking.
     */
    public Task<Void> addUserToList(String mainUserKey, String associatedUserKey) {
        return databaseReference.child(mainUserKey).child(ALL_ASSOCIATED_USERS_ATTRIBUTE).push().setValue(associatedUserKey);
    }

    public String generateNewKey() {
        return databaseReference.push().getKey();
    }
}
