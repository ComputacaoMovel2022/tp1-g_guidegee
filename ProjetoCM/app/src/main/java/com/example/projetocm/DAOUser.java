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
    private final String ALL_REVIEWED_USERS_ATTRIBUTE = "AllReviewedUsers";
    private final String ALL_REQUESTS_USERS_ATTRIBUTE = "AllRequestsUsers";


    public DAOUser() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://guidegee-476d1-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = db.getReference(User.class.getSimpleName()); //"User"

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
     * The snapshot returns a full view of the User table.
     * More info on what you can do with the snapshot:
     * https://firebase.google.com/docs/reference/android/com/google/firebase/database/DataSnapshot
     *
     * Best used if you want to keep a list updated with new users.
     * Other sessions may add new refugees or new guides, so the database will be updated
     * with new information.
     * This method helps with making sure the list is up to date with all the info in the database.
     *
     * TL;DR: Use this method if you just want to update the UI.
     *
     * @param valueEventListener
     */
    public void getDataSnapshotWhenChanged(ValueEventListener valueEventListener) {
        databaseReference.addValueEventListener(valueEventListener);
    }

    /**
     * The snapshot returns a full view of the User table.
     * More info on what you can do with the snapshot:
     * https://firebase.google.com/docs/reference/android/com/google/firebase/database/DataSnapshot
     *
     * Best used if you want to change/add values inside users.
     * If you use the getDataSnapshotWhenChanged to change something inside a user, it will
     * loop endlessly because the value keeps getting changed. Don't make this mistake.
     * Instead, use this method which will only execute once.
     *
     * @param valueEventListener
     */
    public void getDataSnapshotOnce(ValueEventListener valueEventListener) {
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
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

    /**
     * Adds an user to a user list which the main user is associated.
     * Solves the situation of the guides being associated to refugees, and the refugees
     * being associated to guides.
     * You simply put the key of the other in a list of users.
     *
     * @param guideUserKey Main user whose list will get a new key.
     * @param refugeeUserKey that will be added to the mainUser's user list.
     * @return a task, which can be used for error checking.
     */
    public Task<Void> addUserRequestList(String guideUserKey, String refugeeUserKey) {
        return databaseReference.child(guideUserKey).child(ALL_REQUESTS_USERS_ATTRIBUTE).push().setValue(refugeeUserKey);
    }

    /**
     * Adds an user to a reviewed users list which the main user is associated.
     * Solves the situation of the refugees making multiple review's instead of editing their review
     * You simply put the key of the other in a list of reviewed users.
     *
     * @param mainUserKey Main user whose list will get a new key.
     * @param allReviewedUsers that will be added to the reviewed User's user list.
     * @return a task, which can be used for error checking.
     */
    public Task<Void> addReviewToList(String mainUserKey, AllReviewedUsers allReviewedUsers) {
        return databaseReference.child(mainUserKey).child(ALL_REVIEWED_USERS_ATTRIBUTE).push().setValue(allReviewedUsers);
    }

    /**
     * Adds the referred attribute to the user if it doesn't exists (if it exists, it simply gets
     * the attribute) and immediately sets a value.
     *
     * @param userKey The user's key, a String which identifies the user in the database.
     * @param value The value you want to insert in the attribute.
     * @param <T> Generic type.
     * @return a task, which can be used for error checking.
     */
    public <T> Task<Void> setUserReviewValue(String userKey,String allReviewedUsersID, T value) {
        return databaseReference.child(userKey).child("AllReviewedUsers").child(allReviewedUsersID).child("reviewedVal").setValue(value);
    }

    /**
     * Adds the referred attribute to the user if it doesn't exists (if it exists, it simply gets
     * the attribute) and immediately sets a value.
     *
     * @param userKey The user's key, a String which identifies the user in the database.
     * @param attribute The attribute's name.
     * @param value The value you want to insert in the attribute.
     * @param <T> Generic type.
     * @return a task, which can be used for error checking.
     */
    public <T> Task<Void> setUserAttributeValue(String userKey, String attribute, T value) {
        return databaseReference.child(userKey).child(attribute).setValue(value);
    }

    /**
     * Adds a new, empty user to the database.
     * Make sure to fill it with values by using the set method.
     * @return
     */
    public String generateNewKey() {
        return databaseReference.push().getKey();
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
