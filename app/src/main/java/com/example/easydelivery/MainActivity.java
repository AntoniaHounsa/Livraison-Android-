package com.example.easydelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button signInButton, loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         signInButton = findViewById(R.id.SignInButton);
         signInButton.setOnClickListener(this);

         loginButton = findViewById(R.id.loginButton);
         loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == signInButton ){
            Intent myIntent = new Intent (MainActivity.this, SignInActivity.class);
            startActivity(myIntent);
        }
        if(v == loginButton){
            Intent myIntent = new Intent (MainActivity.this, LoginActivity.class);
            startActivity(myIntent);
        }
    }
}