package com.example.easydelivery.presenter.userProfile;
public interface IUserProfilePresenter {
    void loadUserData();
    void updateUserProfile(String newEmail, String newPassword, String newPhoneNumber, String newTruckNumber);
}
