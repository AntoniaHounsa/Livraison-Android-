package com.example.easydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword, editTextphoneNumber;
    Button signInButton;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textViewForLogin;
    private RadioGroup roleRadioGroup;
    private EditText truckNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
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
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignInActivity.this, "Entrer l'email" ,
                                    Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignInActivity.this, "Entrer le mot de passe",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    saveAdditionalUserData(email);
                                    Toast.makeText(SignInActivity.this, "Compte créer",
                                            Toast.LENGTH_SHORT).show();

                                    Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(myIntent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignInActivity.this, "Compte non créér",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });
    }
    private void saveAdditionalUserData(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Récupérez les autres champs (numéro de téléphone, rôle, etc.)
        String phoneNumber = editTextphoneNumber.getText().toString();
        String role = getSelectedRole();
        String truckNumber = null;
        if (role.equals("Chauffeur")) {
            truckNumber = truckNumberEditText.getText().toString();
        }

        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("phoneNumber", phoneNumber);
        user.put("role", role);
        if (truckNumber != null) {
            user.put("truckNumber", truckNumber);
        }

        db.collection("users").add(user)
                .addOnSuccessListener(documentReference -> {
                    // Données enregistrées avec succès
                })
                .addOnFailureListener(e -> {
                    // Échec de l'enregistrement des données
                });
    }
    private String getSelectedRole() {
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

}