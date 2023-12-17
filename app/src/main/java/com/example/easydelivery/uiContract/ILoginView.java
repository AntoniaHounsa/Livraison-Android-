package com.example.easydelivery.uiContract;

public interface ILoginView {
    void showProgress();
    void hideProgress();
    void showSuccessMessage(String message);
    void showErrorMessage(String message);
    void navigateToLogin();
}
