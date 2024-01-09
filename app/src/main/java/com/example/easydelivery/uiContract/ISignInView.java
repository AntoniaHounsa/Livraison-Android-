package com.example.easydelivery.uiContract;

public interface ISignInView {
    void showProgress();
    void hideProgress();
    void showSuccessMessage(String message);
    void showErrorMessage(String message);
    String getPhoneNumber();
    String getRole();
    String getTruckNumber();
    void navigateToLogin();
}
