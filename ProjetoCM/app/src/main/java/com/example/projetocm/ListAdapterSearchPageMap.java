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

public class ListAdapterSearchPageMap extends BaseAdapter {
    private Context context;
    private List<User> guidesList;
    private LayoutInflater inflater;
    private int itemLayout;

    private int currentIndex;


    public ListAdapterSearchPageMap(Context applicationContext, int itemLayout, List<User> guidesList) {
        this.context = applicationContext;
        this.guidesList=guidesList;
        inflater = LayoutInflater.from(applicationContext);
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount() { return this.guidesList.size(); }

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
        ViewHolderSearchPageMap holder;
        holder = new ViewHolderSearchPageMap();
        view = inflater.inflate(itemLayout, null);

        holder.profileName = view.findViewById(R.id.profileNameSearchPageMap);
        holder.profileIcon = view.findViewById(R.id.profileIconSearchPageMap);
        holder.viewMapButton = (Button) view.findViewById (R.id.buttonViewMapSearchPageMap);


        holder.profileName.setText(guidesList.get(i).getUsername());
        if(guidesList.get(i).getImageURL()==null || guidesList.get(i).getImageURL().equals("")){
            holder.profileIcon.setImageResource(R.drawable.empty_profile_icon);
        }else {
            Picasso.get().load(guidesList.get(i).getImageURL()).into(holder.profileIcon);
        }
        currentIndex = i;
        holder.viewMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mapAct = new Intent(context,MapActivity.class);
                mapAct.putExtra("guideKey", guidesList.get(currentIndex).getUserKey());
                mapAct.putExtra("myLat", GPSManager.lastLocation.getLatitude());
                mapAct.putExtra("myLong", GPSManager.lastLocation.getLongitude());
                context.startActivity(mapAct);

            }

        });


        return view;
    }

    public void sendContext(Context context){
        this.context = context;
    }

    class ViewHolderSearchPageMap {
        TextView profileName;
        CircleImageView profileIcon;
        Button viewMapButton;
    }
}
