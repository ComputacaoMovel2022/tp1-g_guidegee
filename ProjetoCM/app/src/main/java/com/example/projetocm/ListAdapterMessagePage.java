package com.example.projetocm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapterMessagePage extends BaseAdapter {
    private Context context;
    private List<ChatMessage> message;
    private LayoutInflater inflater;
    private int itemLayoutSender;
    private int itemLayoutReceiver;
    private String imageURLSender;
    private String imageURLReceiver;


    public ListAdapterMessagePage(Context context, List<ChatMessage> message, int itemLayoutSender, int itemLayoutReceiver, String imageURLSender, String imageURLReceiver){
        this.context=context;
        this.message=message;
        inflater = LayoutInflater.from(context);
        this.itemLayoutSender = itemLayoutSender;
        this.itemLayoutReceiver = itemLayoutReceiver;
        this.imageURLReceiver = imageURLReceiver;
        this.imageURLSender = imageURLSender;
    }

    @Override
    public int getCount() {
        return message.size();
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
        String loggedUserKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        boolean isSenderTheLoggedUser = message.get(i).getMessageSenderKey().equals(loggedUserKey);
        if (isSenderTheLoggedUser) {
            view = inflater.inflate(itemLayoutSender, null);
        } else {
            view = inflater.inflate(itemLayoutReceiver, null);
        }
        ListAdapterMessagePage.ViewHolder holder;
        holder = new ListAdapterMessagePage.ViewHolder();
        holder.message = view.findViewById(R.id.messageTextView);
        holder.time = view.findViewById(R.id.messageTimeView);
        holder.profileIcon = view.findViewById(R.id.profileIconForMessage);

        if (isSenderTheLoggedUser) {
            if (imageURLSender.isEmpty()) {
                holder.profileIcon.setImageResource(R.drawable.empty_profile_icon);
            } else {
                Picasso.get().load(imageURLSender).into(holder.profileIcon);
            }
        } else {
            if (imageURLReceiver.isEmpty()) {
                holder.profileIcon.setImageResource(R.drawable.empty_profile_icon);
            } else {
                Picasso.get().load(imageURLReceiver).into(holder.profileIcon);
            }
        }

        DateFormat dateFormat;
        if (isSenderTheLoggedUser) {
            dateFormat = new SimpleDateFormat("HH:mm dd/MM/yy");
        } else {
            dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
        }

        Date date = new Date(message.get(i).getMessageTime());
        String formattedDate = dateFormat.format(date);
        holder.time.setText(formattedDate);
        holder.message.setText(message.get(i).getMessageText());



        return view;
    }

    class ViewHolder {
        TextView message;
        CircleImageView profileIcon;
        TextView time;
    }
}
