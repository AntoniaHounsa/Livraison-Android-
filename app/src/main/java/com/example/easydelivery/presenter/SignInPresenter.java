package com.example.easydelivery.presenter;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.easydelivery.model.Cart;
import com.example.easydelivery.model.User;
import com.example.easydelivery.repository.CartRepository;
import com.example.easydelivery.repository.ICartRepository;
import com.example.easydelivery.repository.IUserRepository;
import com.example.easydelivery.repository.UserRepository;
import com.example.easydelivery.uiContract.ISignInView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class SignInPresenter implements ISignInPresenter {
    private ISignInView view;
    private IUserRepository userRepository;
    private ICartRepository cartRepository;

    public SignInPresenter(ISignInView view) {
        this.view = view;
        this.userRepository = new UserRepository();
        this.cartRepository = new CartRepository();

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

        userRepository.createUser(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //je récupère l'UID pour connecter l'utilisateur dans firebase et firestore
                            String uid = userRepository.getUserUIDFromFauth();
                            saveAdditionalUserData(email, uid);
                            createAssociatedCart(uid);
                            view.hideProgress();
                            view.showSuccessMessage("Compte créer");

                           view.navigateToLogin();

                        } else {
                            view.showErrorMessage("compte non créer");

                        }
                    }
                });


    }
    private void saveAdditionalUserData(String email, String uid) {
        // Création de l'objet User
        User user = new User();
        user.setId(uid);
        user.setEmail(email);
        user.setPhoneNumber(view.getPhoneNumber());
        user.setRole(view.getRole());

        if (user.getRole().equals("Chauffeur")) {
            user.setTruckNumber(view.getTruckNumber());
        }

        userRepository.saveUser(user)
        .addOnSuccessListener(aVoid -> {
                })
                .addOnFailureListener(e -> {
                    view.showErrorMessage("Echec lors de la création de l'tuilisateur");
                });
    }

    private void createAssociatedCart(String uid){
        Cart myCart = new Cart();
        myCart.setUserId(uid);


        cartRepository.createCartForUser(uid)
        .addOnSuccessListener(aVoid ->{

                })
                .addOnFailureListener(e ->{

                });
    }

}
