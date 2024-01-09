package com.example.easydelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easydelivery.presenter.SignInPresenter;
import com.example.easydelivery.uiContract.ISignInView;
import com.google.android.material.textfield.TextInputEditText;


public class SignInActivity extends AppCompatActivity implements ISignInView {
    TextInputEditText editTextEmail, editTextPassword, editTextphoneNumber;
    Button signInButton;
    ProgressBar progressBar;
    TextView textViewForLogin;
    private RadioGroup roleRadioGroup;
    private EditText truckNumberEditText;
    private SignInPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        presenter = new SignInPresenter(this);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        signInButton =  findViewById(R.id.SignInButton);
        progressBar = findViewById(R.id.progressBar);
        textViewForLogin = findViewById(R.id.loginNow);
        editTextphoneNumber = findViewById(R.id.tel);
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        truckNumberEditText = findViewById(R.id.immatriculation);


        roleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Cache le champ du numéro d'immatriculation à moins que le rôle de chauffeur ne soit sélectionné
                if (checkedId == R.id.radioDriver) {
                    truckNumberEditText.setVisibility(View.VISIBLE);
                } else {
                    truckNumberEditText.setVisibility(View.GONE);
                }
            }
        });
        textViewForLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
        signInButton.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            presenter.signIn(email, password);
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
        Toast.makeText(SignInActivity.this, message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(SignInActivity.this, message ,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getPhoneNumber() {
        String phoneNumber = editTextphoneNumber.getText().toString();
        return  phoneNumber;
    }

    @Override
    public String getRole() {
        int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();
        if (selectedRoleId == R.id.radioBuyer) {
            return "Acheteur";
        } else if (selectedRoleId == R.id.radioPlanner) {
            return "Planificateur";
        } else if (selectedRoleId == R.id.radioDriver) {
            return "Chauffeur";
        } else {
            return null;
        }
    }

    @Override
    public String getTruckNumber() {
        return truckNumberEditText.getText().toString();
    }

    @Override
    public void navigateToLogin() {

        Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(myIntent);
        finish();
    }
}