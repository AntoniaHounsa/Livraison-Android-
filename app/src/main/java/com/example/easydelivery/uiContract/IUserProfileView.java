package com.example.easydelivery.uiContract;

import com.example.easydelivery.model.User;

public interface IUserProfileView {
    void showUserData(User user);
    void showErrorMessage(String message);
    void showSuccessMessage(String message);
    void updateUserEmail(String email);
    void updateUserPhoneNumber(String phoneNumber);
    void updateUserTruckNumber(String truckNumber);
}
