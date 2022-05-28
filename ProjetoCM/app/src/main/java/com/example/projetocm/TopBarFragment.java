package com.example.projetocm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class TopBarFragment extends Fragment {

    DAOUser daoUser = new DAOUser();

    public TopBarFragment() {
        super(R.layout.top_bar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_bar,
                container, false);
        ImageView settingsButton = view.findViewById(R.id.settingsTopBar);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                view.getContext().startActivity(new Intent(view.getContext(), Settings.class));
                getActivity().finish();
            }
        });

        ImageView historyButton = view.findViewById(R.id.historyTopBar);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = view.getContext().getSharedPreferences("userDefinitions", Context.MODE_PRIVATE);
                String isGuideString = preferences.getString("isGuide", "");
                if (isGuideString.equals("true")) {
                    view.getContext().startActivity(new Intent(view.getContext(), GuideHistory.class));
                } else {
                    view.getContext().startActivity(new Intent(view.getContext(), RefugeeHistory.class));
                }
            }
        });

        ImageView sendMsgButton = view.findViewById(R.id.messageSendButton);
        sendMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(), MessageListPage.class));
            }
        });
        return view;
    }
}
