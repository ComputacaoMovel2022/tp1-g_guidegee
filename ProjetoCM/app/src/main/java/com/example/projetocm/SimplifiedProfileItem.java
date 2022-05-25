package com.example.projetocm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Reutilizable base adapter.
 * Used for lists of profiles. It can only change the profile picture and the profile name.
 * Everything else in the passed layout remains unchanged.
 * Pages it can be used for: Guide History, Refugee History, Message List
 */
public class SimplifiedProfileItem extends BaseAdapter {

    private Context context;
    private List<User> allUsers;
    private LayoutInflater inflater;
    private int itemLayout;

    public SimplifiedProfileItem(Context applicationContext, int itemLayout, List<User> allUsers) {
        this.context = applicationContext;
        this.allUsers = allUsers;
        inflater = LayoutInflater.from(applicationContext);
        this.itemLayout = itemLayout;
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
        TextView profileName = view.findViewById(R.id.profileName);
        CircleImageView profileIcon = view.findViewById(R.id.profileIcon);

        profileName.setText(allUsers.get(i).getUsername());

        if (allUsers.get(i).getImageURL() == null || allUsers.get(i).getImageURL().equals("")) {
            profileIcon.setImageResource(R.drawable.empty_profile_icon);
        } else {
            Picasso.get().load(allUsers.get(i).getImageURL()).into(profileIcon);
        }

        // TODO: Open the profile page when the button is clicked
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}
