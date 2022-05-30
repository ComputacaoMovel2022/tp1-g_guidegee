package com.example.projetocm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class BottomBarFragment extends Fragment {

    public BottomBarFragment() {
        super(R.layout.bottom_bar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_bar,
                container, false);
        ImageView homeButton = view.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                view.getContext().startActivity(new Intent(view.getContext(), HomeActivity.class));
                getActivity().finish();
            }
        });

        ImageView profileButton = view.findViewById(R.id.profileBottomButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Profile.class);
                intent.putExtra("user", FirebaseAuth.getInstance().getCurrentUser().getUid());
                view.getContext().startActivity(intent);
            }
        });

        ImageView searchButton = view.findViewById(R.id.searchButtonImageView);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = view.getContext().getSharedPreferences("userDefinitions", Context.MODE_PRIVATE);
                String isGuideString = preferences.getString("isGuide", "");
                if (isGuideString.equals("false")) {
                    Intent intent = new Intent(view.getContext(), SearchPageMap.class);
                    view.getContext().startActivity(intent);
                }else{
                    Intent intent = new Intent(view.getContext(), RequestPage.class);
                    view.getContext().startActivity(intent);
                }
            }
        });

        return view;
    }

}
