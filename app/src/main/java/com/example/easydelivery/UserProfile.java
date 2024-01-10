package com.example.easydelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.easydelivery.model.User;
import com.example.easydelivery.presenter.userProfile.IUserProfilePresenter;
import com.example.easydelivery.presenter.userProfile.UserProfilePresenter;
import com.example.easydelivery.uiContract.IUserProfileView;
import com.google.android.material.textfield.TextInputEditText;

public class UserProfile extends AppCompatActivity implements IUserProfileView {
    TextInputEditText editTextEmail, editTextPassword, editTextPhoneNumber, editTruckNumber;
    Button modifyButton;
    IUserProfilePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextPhoneNumber = findViewById(R.id.tel);
        editTruckNumber = findViewById(R.id.immatriculation);
        modifyButton = findViewById(R.id.ModifyButton);

        presenter = new UserProfilePresenter(this);
        presenter.loadUserData();

        modifyButton.setOnClickListener(v -> {
            String newEmail = editTextEmail.getText().toString();
            String newPassword = editTextPassword.getText().toString();
            String newPhoneNumber = editTextPhoneNumber.getText().toString();
            String newTruckNumber = editTruckNumber.getText().toString();

            presenter.updateUserProfile(newEmail, newPassword, newPhoneNumber, newTruckNumber);
        });
    }

    @Override
    public void showUserData(User user) {
        editTextEmail.setText(user.getEmail());
        editTextPhoneNumber.setText(user.getPhoneNumber());
        if("Chauffeur".equals(user.getRole())){
            editTruckNumber.setVisibility(View.VISIBLE);
            editTruckNumber.setText(user.getTruckNumber());
        } else {
            editTruckNumber.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUserEmail(String email) {
        editTextEmail.setText(email);
    }

    @Override
    public void updateUserPhoneNumber(String phoneNumber) {
        editTextPhoneNumber.setText(phoneNumber);
    }

    @Override
    public void updateUserTruckNumber(String truckNumber) {
        editTruckNumber.setText(truckNumber);
    }
}
