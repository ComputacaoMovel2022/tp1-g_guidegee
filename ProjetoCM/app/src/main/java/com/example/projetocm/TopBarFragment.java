package com.example.projetocm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class TopBarFragment extends Fragment {

    public TopBarFragment() {
        super(R.layout.top_bar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_bar,
                container, false);
        ImageView settingsButton = view.findViewById(R.id.settingsTopBar);
        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                view.getContext().startActivity(new Intent(view.getContext(), Settings.class));
                getActivity().finish();
            }
        });
        return view;
    }
}
