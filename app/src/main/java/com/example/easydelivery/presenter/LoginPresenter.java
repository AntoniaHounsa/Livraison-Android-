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

public class LoginPresenter implements ILoginPresenter{
    private ILoginView view;
    private FirebaseAuth mAuth;
    public LoginPresenter(ILoginView view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void login(String email, String password) {
        view.showProgress();


        if(TextUtils.isEmpty(email)){
           view.showErrorMessage("Entrer l'email");
            return;
        }
        if(TextUtils.isEmpty(password)){
           view.showErrorMessage("Entrer le mot de passe");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        view.hideProgress();
                        if (task.isSuccessful()) {
                            view.showSuccessMessage("Connexion réussie");
                            view.navigateToLogin();
                        } else {
                           view.showErrorMessage("Connexion échouée");
                        }
                    }
                });


    }
}
