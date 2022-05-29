package com.example.projetocm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapterBestGuide extends BaseAdapter {

    private Context context;
    private List<User> guidesList;
    private LayoutInflater inflater;
    private int itemLayout;

    public ListAdapterBestGuide(Context applicationContext, int itemLayout, List<User> guidesList){
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
        ViewHolderBestGuide holder;
        holder = new ViewHolderBestGuide();
        view = inflater.inflate(itemLayout, null);

        if(guidesList.get(i)!=null){
            holder.profileIcon = view.findViewById(R.id.profileIconBestGuide);
            holder.profileName = view.findViewById(R.id.profileNameBestGuide);
            holder.rankingValue = view.findViewById(R.id.rakingBestGuide);
            holder.scoreValue = view.findViewById(R.id.scoreValueBestGuide);

            holder.profileName.setText(guidesList.get(i).getUsername());
            if (guidesList.get(i).getImageURL() == null || guidesList.get(i).getImageURL().equals("")) {
                holder.profileIcon.setImageResource(R.drawable.empty_profile_icon);
            } else {
                Picasso.get().load(guidesList.get(i).getImageURL()).into(holder.profileIcon);
            }
            holder.rankingValue.setText(String.valueOf(i + 1));
            /* Equation */
            double res = guidesList.get(i).getRatingScore() + guidesList.get(i).getNumOfPplHelped() * 2;
            holder.scoreValue.setText(String.valueOf(res));
        }
        return view;
    }

    class ViewHolderBestGuide {
        TextView profileName;
        CircleImageView profileIcon;
        TextView rankingValue;
        TextView scoreValue;
    }
}
