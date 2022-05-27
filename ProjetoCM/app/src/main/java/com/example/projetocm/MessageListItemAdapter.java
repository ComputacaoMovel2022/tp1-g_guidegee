package com.example.projetocm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageListItemAdapter extends BaseAdapter {
    private Context context;
    private List<User> allUsers;
    private LayoutInflater inflater;
    private int itemLayout;

    public MessageListItemAdapter(Context applicationContext, int itemLayout, List<User> allUser) {
        this.context = applicationContext;
        this.allUsers = allUser;
        inflater = LayoutInflater.from(applicationContext);
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount() {
        return allUsers.size();
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
        System.out.println("GETTING IN HERE");
        view = inflater.inflate(itemLayout, null);

        TextView textView = view.findViewById(R.id.messageProfileName);
        CircleImageView imageView = view.findViewById(R.id.messageProfileIcon);

        textView.setText(allUsers.get(i).getUsername());
        if (allUsers.get(i).getImageURL() == null || allUsers.get(i).getImageURL().equals("")) {
            imageView.setImageResource(R.drawable.empty_profile_icon);
        } else {
            Picasso.get().load(allUsers.get(i).getImageURL()).into(imageView);
        }

        // TODO: Open the messages correspondence between the user and the other user
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessagePageActivity.class);
                intent.putExtra("messageReceiver", allUsers.get(i).getUserKey());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }
}
