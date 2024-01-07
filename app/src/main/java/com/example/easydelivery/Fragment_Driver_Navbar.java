package com.example.easydelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Fragment_Driver_Navbar extends Fragment {
    FirebaseUser user;
    TextView textView;
    FirebaseAuth auth;
    ImageView userlogo, userCart, home;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Je fais ça pour relier le fichier java à la vue xml
        View view = inflater.inflate(R.layout.fragment_driver_navbar, container, false);

        Button logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        home = view.findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AfterLoginDriver.class);
                startActivity(intent);
            }
        });

        textView = view.findViewById(R.id.user_details);
        // Initialisation de FirebaseAuth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null){
            Intent myIntent = new Intent (getActivity(), LoginActivity.class);
            startActivity(myIntent);

        }
        else{
            textView.setText(user.getEmail());
        }

        userlogo = view.findViewById(R.id.userLogo);
        userlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), UserProfile.class);
                startActivity(myIntent);
            }
        });

        userCart = view.findViewById(R.id.history);
        userCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), FinishedMissions.class);
                startActivity(myIntent);
            }
        });

        return view;
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
