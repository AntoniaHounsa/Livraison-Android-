package com.example.easydelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.easydelivery.presenter.login.LoginPresenter;
import com.example.easydelivery.uiContract.ILoginView;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    TextInputEditText editTextEmail, editTextPassword;
    Button loginButton;
    ProgressBar progressBar;
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);


        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);




        loginButton.setOnClickListener(v->{
            String email, password;
            email = editTextEmail.getText().toString();
            password = editTextPassword.getText().toString();
            presenter.login(email,password);
        });


    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(LoginActivity.this, message ,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(LoginActivity.this, message ,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToLogin(String role) {

        if("Acheteur".equals(role)){
            Intent myIntent = new Intent(getApplicationContext(), AfterLogin.class);
            startActivity(myIntent);
            finish();
        }
        if("Planificateur".equals(role)){
            Intent myIntent = new Intent(getApplicationContext(), AfterLoginPlanificateur.class);
            startActivity(myIntent);
            finish();
        }
        if("Chauffeur".equals(role)){
            Intent myIntent = new Intent(getApplicationContext(), AfterLoginDriver.class);
            startActivity(myIntent);
            finish();
        }

    }
}