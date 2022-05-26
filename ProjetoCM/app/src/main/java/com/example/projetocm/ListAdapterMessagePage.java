package com.example.projetocm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapterMessagePage extends BaseAdapter {
    private Context context;
    private String[] message;
    private int[] profileItemPictures;
    private LayoutInflater inflater;
    private int itemLayout;


    public ListAdapterMessagePage(Context context, String[] message, int[] profileItemPictures, int itemLayout){
        this.context=context;
        this.message=message;
        this.profileItemPictures=profileItemPictures;
        inflater = LayoutInflater.from(context);
        this.itemLayout=itemLayout;

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
        ListAdapterMessagePage.ViewHolder holder;
        holder = new ListAdapterMessagePage.ViewHolder();
        view = inflater.inflate(itemLayout, null);
        holder.message = view.findViewById(R.id.messageTextView);
        holder.profileIcon = view.findViewById(R.id.profileIconForMessage);

        holder.profileIcon.setImageResource(profileItemPictures[i]);
        holder.message.setText(message[i]);

        return view;
    }

    class ViewHolder {
        TextView message;
        CircleImageView profileIcon;
    }
}
