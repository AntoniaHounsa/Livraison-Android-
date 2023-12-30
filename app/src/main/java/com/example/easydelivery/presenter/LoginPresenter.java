package com.example.easydelivery.presenter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.easydelivery.AfterLogin;
import com.example.easydelivery.LoginActivity;
import com.example.easydelivery.uiContract.ILoginView;
import com.example.easydelivery.uiContract.ISignInView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPresenter implements ILoginPresenter{
    private ILoginView view;
    private FirebaseAuth mAuth;
    public LoginPresenter(ILoginView view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }



    @Override
    public void login(String email, String password) {
        // ... vérification de l'email et du mot de passe ...
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Après une connexion réussie, obtenir l'UID de l'utilisateur connecté
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            // Effectuer une requête pour obtenir le rôle de l'utilisateur
                            FirebaseFirestore.getInstance().collection("users").document(userId)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            view.hideProgress();
                                            if (task.isSuccessful() && task.getResult() != null) {
                                                String role = task.getResult().getString("role");
                                                // Passer le rôle à la méthode navigateToLogin
                                                view.navigateToLogin(role);
                                            } else {
                                                view.showErrorMessage("Impossible de récupérer le rôle de l'utilisateur");
                                            }
                                        }
                                    });
                        } else {
                            view.hideProgress();
                            view.showErrorMessage("Connexion échouée");
                        }
                    }
                });
    }


}
