package com.example.easydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.easydelivery.presenter.LoginPresenter;
import com.example.easydelivery.uiContract.ILoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    TextInputEditText editTextEmail, editTextPassword;
    Button loginButton;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);

        mAuth = FirebaseAuth.getInstance();
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