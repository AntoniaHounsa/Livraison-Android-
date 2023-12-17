package com.example.easydelivery;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {
    TextInputEditText editTextEmail,editTextPassword, editTextphoneNumber, editTruckNumber;
    Button modifyButton;
    FirebaseUser firebaseUser ;
    FirebaseFirestore db ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextphoneNumber = findViewById(R.id.tel);
        editTextPassword = findViewById(R.id.password);
        editTruckNumber = findViewById(R.id.immatriculation);

        if (firebaseUser != null) {
            // Récupérer l'email depuis Firebase Auth
            String currentEmail = firebaseUser.getEmail();
            editTextEmail.setText(currentEmail);

            // Récupérer d'autres informations depuis Firestore
            db.collection("users").document(firebaseUser.getUid()).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);

                            editTextphoneNumber.setText(user.getPhoneNumber());

                            if("Chauffeur".equals(user.getRole())){
                                editTruckNumber.setVisibility(View.VISIBLE);
                                editTruckNumber.setText(user.getTruckNumber());
                            }

                        }
                        else {
                            Toast.makeText(this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                    });
        }

        modifyButton = findViewById(R.id.ModifyButton);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération des nouvelles valeurs entrées par l'utilisateur
                String newEmail = editTextEmail.getText().toString();
                String newPhoneNumber = editTextphoneNumber.getText().toString();
                String newPassword = editTextPassword.getText().toString();
                String newTruckNumber = editTruckNumber.getText().toString();

                    // Mise à jour de l'adresse électronique dans Firebase Authentication
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        if (!TextUtils.isEmpty(newEmail)){
                            user.updateEmail(newEmail)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User email address updated.");
                                            // Informer l'utilisateur de la réussite via un Toast ou une autre méthode
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.w(TAG, "Error updating email: ", e);
                                        // Informer l'utilisateur de l'échec
                                        Toast.makeText(UserProfile.this, "La mise à jour de l'email a échoué : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                                        // Si nécessaire, réauthentifier l'utilisateur ici
                                    });

                        }
                        if (!TextUtils.isEmpty(newPassword)){
                            user.updatePassword(newPassword)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {

                                        }
                                    });
                        }
                        if (!TextUtils.isEmpty(newPhoneNumber)){
                            // Mise à jour du numéro de téléphone et d'autres informations dans Firestore
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("phoneNumber", newPhoneNumber);
                            // ... ajouter d'autres champs à mettre à jour si nécessaire

                            db.collection("users").document(user.getUid()).update(updates)
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "User profile updated."))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error updating profile", e));
                        }
                        if (!TextUtils.isEmpty(newTruckNumber)){
                            // Mise à jour du numéro de téléphone et d'autres informations dans Firestore
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("truckNumber", newTruckNumber);


                            db.collection("users").document(user.getUid()).update(updates)
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "User profile updated."))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error updating profile", e));
                        }


                        Toast.makeText(UserProfile.this, "Mis à jour réussie", Toast.LENGTH_SHORT).show();


                    }




            }
        });

    }

}