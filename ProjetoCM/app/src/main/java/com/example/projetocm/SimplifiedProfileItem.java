package com.example.projetocm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Reutilizable base adapter.
 * Used for lists of profiles. It can only change the profile picture and the profile name.
 * Everything else in the passed layout remains unchanged.
 * Pages it can be used for: Guide History, Refugee History, Message List
 */
public class SimplifiedProfileItem extends BaseAdapter {

    private Context context;
    private String[] profileItemNames;
    private int[] profileItemPictures;
    private LayoutInflater inflater;
    private int itemLayout;

    public SimplifiedProfileItem(Context applicationContext, int itemLayout, String[] profileNames, int[] profilePictures) {
        this.context = applicationContext;
        this.profileItemNames = profileNames;
        this.profileItemPictures = profilePictures;
        inflater = LayoutInflater.from(applicationContext);
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount() {
        return profileItemNames.length;
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

        profileName.setText(profileItemNames[i]);
        profileIcon.setImageResource(profileItemPictures[i]);

        return view;
    }
}
