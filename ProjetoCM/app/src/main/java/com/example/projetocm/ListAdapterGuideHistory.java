package com.example.projetocm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Reutilizable base adapter.
 * Used for lists of profiles. It can only change the profile picture and the profile name.
 * Everything else in the passed layout remains unchanged.
 * Pages it can be used for: Guide History, Refugee History, Message List
 */

public class ListAdapterGuideHistory extends BaseAdapter{

    private Context context;
    private String[] profileItemNames;
    private int[] profileItemPictures;
    private LayoutInflater inflater;
    private int itemLayout;


    public ListAdapterGuideHistory(Context applicationContext, int itemLayout, String[] profileNames, int[] profilePictures) {
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
        ViewHolder holder;
        holder = new ViewHolder();
        view = inflater.inflate(itemLayout, null);

        holder.profileName = view.findViewById(R.id.profileName);
        holder.profileIcon = view.findViewById(R.id.profileIcon);
        holder.reviewButton = (Button) view.findViewById (R.id.buttonReview);


        holder.profileName.setText(profileItemNames[i]);
        holder.profileIcon.setImageResource(profileItemPictures[i]);
        holder.reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itRatingGuide = new Intent(context,RatingGuide.class);
                itRatingGuide.putExtra("guideName",holder.profileName.getText().toString());
                 context.startActivity(itRatingGuide);
            }

        });


        return view;
    }

    public void sendContext(Context context){
        this.context = context;
    }

    class ViewHolder {
        TextView profileName;
        CircleImageView profileIcon;
        Button reviewButton;
    }

}
