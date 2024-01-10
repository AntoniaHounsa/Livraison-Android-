package com.example.easydelivery.presenter.userProfile;

import android.text.TextUtils;

import com.example.easydelivery.model.User;
import com.example.easydelivery.repository.user.IUserRepository;
import com.example.easydelivery.repository.user.UserRepository;
import com.example.easydelivery.uiContract.IUserProfileView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class UserProfilePresenter implements IUserProfilePresenter {
    private IUserProfileView view;
    private IUserRepository userRepository;

    public UserProfilePresenter(IUserProfileView view) {
        this.view = view;
        this.userRepository = new UserRepository();
    }

    @Override
    public void loadUserData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userRepository.getUser(currentUser.getUid())
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            view.showUserData(user);
                        } else {
                            view.showErrorMessage("User data not found");
                        }
                    })
                    .addOnFailureListener(e -> view.showErrorMessage("Error loading user data"));
        }
    }

    @Override
    public void updateUserProfile(String newEmail, String newPassword, String newPhoneNumber, String newTruckNumber) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            Map<String, Object> updates = new HashMap<>();
            updates.put("phoneNumber", newPhoneNumber);
            updates.put("truckNumber", newTruckNumber);

            userRepository.updateUser(userId, updates)
                    .addOnSuccessListener(aVoid -> view.showSuccessMessage("User profile updated"))
                    .addOnFailureListener(e -> view.showErrorMessage("Error updating profile"));

            if (!TextUtils.isEmpty(newEmail)) {
                userRepository.updateEmail(newEmail)
                        .addOnSuccessListener(aVoid -> view.updateUserEmail(newEmail))
                        .addOnFailureListener(e -> view.showErrorMessage("Error updating email"));
            }

            if (!TextUtils.isEmpty(newPassword)) {
                userRepository.updatePassword(newPassword)
                        .addOnSuccessListener(aVoid -> view.showSuccessMessage("Password updated"))
                        .addOnFailureListener(e -> view.showErrorMessage("Error updating password"));
            }
        }
    }
}
