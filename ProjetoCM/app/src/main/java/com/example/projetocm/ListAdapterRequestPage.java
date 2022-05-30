package com.example.projetocm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapterRequestPage extends BaseAdapter {
    private DAOUser daoUser;

    private Context context;
    private List<User> refugeesList;
    private LayoutInflater inflater;
    private int itemLayout;
    private String guideKey;

    private int currentIndex;


    public ListAdapterRequestPage(Context applicationContext, int itemLayout, List<User> refugeesList,String guideKey) {
        this.context = applicationContext;
        this.refugeesList=refugeesList;
        inflater = LayoutInflater.from(applicationContext);
        this.itemLayout = itemLayout;
        this.guideKey = guideKey;
        daoUser = new DAOUser();
    }

    @Override
    public int getCount() { return this.refugeesList.size(); }

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
        ViewHolderRequestPage holder;
        holder = new ViewHolderRequestPage();
        view = inflater.inflate(itemLayout, null);

        holder.profileName = view.findViewById(R.id.requestUsername);
        holder.profileIcon = view.findViewById(R.id.requestProfileIcon);
        holder.acceptButton = (Button) view.findViewById(R.id.acceptRequestButton);
        holder.declineButton = (Button) view.findViewById(R.id.declineRequestButton);


        holder.profileName.setText(refugeesList.get(i).getUsername());
        if (refugeesList.get(i).getImageURL() == null || refugeesList.get(i).getImageURL().equals("")) {
            holder.profileIcon.setImageResource(R.drawable.empty_profile_icon);
        } else {
            Picasso.get().load(refugeesList.get(i).getImageURL()).into(holder.profileIcon);
        }
        currentIndex = i;

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gets in AllAsociated
                daoUser.addUserToList(guideKey, refugeesList.get(currentIndex).getUserKey());
                daoUser.addUserToList(refugeesList.get(currentIndex).getUserKey(), guideKey);

                daoUser.removeUserRequest(guideKey, refugeesList.get(currentIndex).getUserKey());
            }

        });

        holder.declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //doesn't gets in AllAsociated
                daoUser.removeUserRequest(guideKey, refugeesList.get(currentIndex).getUserKey());
            }

        });
        return view;
    }

    public void sendContext(Context context){
        this.context = context;
    }

    class ViewHolderRequestPage {
        TextView profileName;
        CircleImageView profileIcon;
        Button acceptButton;
        Button declineButton;
    }
}
