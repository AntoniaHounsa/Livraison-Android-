package com.example.easydelivery.presenter;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.easydelivery.LoginActivity;
import com.example.easydelivery.SignInActivity;
import com.example.easydelivery.uiContract.ISignInView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignInPresenter implements ISignInPresenter {
    private ISignInView view;
    private FirebaseAuth mAuth;

    public SignInPresenter(ISignInView view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signIn(String email, String password) {
        view.showProgress();


        if(TextUtils.isEmpty(email)){
           view.showErrorMessage("Entrer l'email");
            return;
        }
        if(TextUtils.isEmpty(password)){
           view.showErrorMessage("Entrez le mot de passe");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //je récupère l'UID pour connecter l'utilisateur dans firebase et firestore
                            String uid = mAuth.getCurrentUser().getUid();
                            saveAdditionalUserData(email, uid);
                            view.hideProgress();
                            view.showSuccessMessage("Compte créer");

                           view.navigateToLogin();

                        } else {
                            // If sign in fails, display a message to the user.
                            view.showErrorMessage("compte non créer");

                        }
                    }
                });


    }
    private void saveAdditionalUserData(String email, String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Récupérez les autres champs (numéro de téléphone, rôle, etc.)
        String phoneNumber = view.getPhoneNumber();
        String role = view.getRole();
        String truckNumber = null;
        if (role.equals("Chauffeur")) {
            truckNumber = view.getTruckNumber();
        }

        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("phoneNumber", phoneNumber);
        user.put("role", role);
        if (truckNumber != null) {
            user.put("truckNumber", truckNumber);
        }

        db.collection("users").document(uid).set(user)
                .addOnSuccessListener(aVoid -> {
                    //Toast.makeText(this,"Les données ont bien été enregistrer ");
                })
                .addOnFailureListener(e -> {
                    // Échec de l'enregistrement des données
                });
    }

}
