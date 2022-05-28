package com.example.projetocm;

import android.content.Intent;
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
                daoUser.getDatabaseReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user.isUserGuide()) {
                            view.getContext().startActivity(new Intent(view.getContext(), GuideHistory.class));
                        } else {
                            view.getContext().startActivity(new Intent(view.getContext(), RefugeeHistory.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return view;
    }
}
