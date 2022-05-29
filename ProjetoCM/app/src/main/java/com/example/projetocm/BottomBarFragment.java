package com.example.projetocm;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BottomBarFragment extends Fragment {

    public BottomBarFragment() {
        super(R.layout.bottom_bar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_bar,
                container, false);

        GPSManager gpsManager = new GPSManager();
        Location loc = gpsManager.getLocationWithPermission(getActivity());

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

        SharedPreferences preferences = view.getContext().getSharedPreferences(
                "userDefinitions", MODE_PRIVATE
        );
        boolean isGuide = preferences.getString("isGuide", "").equals("true");

        ImageView searchButton = view.findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Toast.makeText(
                        view.getContext(),
                        String.format("Logged as %s...", (isGuide) ? "Guide" : "Refugee"),
                        Toast.LENGTH_LONG
                ).show();

                Intent intent = new Intent(view.getContext(), MapActivity.class);
                intent.getExtras().putDouble("myLatitude", loc.getLatitude());
                intent.getExtras().putDouble("myLongitude", loc.getLongitude());
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }

}
