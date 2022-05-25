package com.example.projetocm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestFromRefugeeAdapter extends BaseAdapter {
    private List<User> userRequests;
    private LayoutInflater inflater;
    private int itemLayout;
    private String loggedUserKey;
    private DAOUser daoUser;

    public RequestFromRefugeeAdapter(Context applicationContext, int itemLayout, List<User> userRequests, String loggedUserKey) {
        this.userRequests = userRequests;
        this.itemLayout = itemLayout;
        this.loggedUserKey = loggedUserKey;
        this.inflater = LayoutInflater.from(applicationContext);
        daoUser = new DAOUser();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(itemLayout, null);
        CircleImageView profileIcon = view.findViewById(R.id.requestProfileIcon);
        TextView profileName = view.findViewById(R.id.requestUsername);
        AppCompatButton acceptButton = view.findViewById(R.id.acceptRequestButton);
        AppCompatButton declineButton = view.findViewById(R.id.declineRequestButton);
        User thisRequestUser = userRequests.get(i);

        if (thisRequestUser.getImageURL() == null || thisRequestUser.getImageURL().equals("")) {
            profileIcon.setImageResource(R.drawable.empty_profile_icon);
        } else {
            Picasso.get().load(thisRequestUser.getImageURL()).into(profileIcon);
        }
        profileName.setText(thisRequestUser.getUsername());

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daoUser.getDataSnapshotOnce(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        removeThisRequest(snapshot, thisRequestUser);
                        daoUser.addUserToList(loggedUserKey, thisRequestUser.getUserKey());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daoUser.getDataSnapshotOnce(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        removeThisRequest(snapshot, thisRequestUser);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        return view;
    }

    /**
     * Removes a request from the logged guide's list of requests.
     *
     * @param snapshot The snapshot containing the User table, which has all the users.
     * @param thisRequestUser The user you want to remove.
     */
    public void removeThisRequest(DataSnapshot snapshot, User thisRequestUser) {
        for (DataSnapshot ds: snapshot.child(loggedUserKey).child("allRequests").getChildren()) {
            if (ds.getValue(String.class).equals(thisRequestUser.getUserKey())) {
                daoUser.getDatabaseReference().child("allRequests").child(ds.getKey()).removeValue();
            }
        }
    }
}
