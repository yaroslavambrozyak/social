package com.example.yaroslav.scorpionssocial.view.interfaces;

public interface LoginActivityInterface {

    void onLoginButtonClick();

    void onRegistrationButtonClick();

    void showLoginErrorMessage(String message);

    void showPasswordErrorMessage(String message);

    void showProgressDialog();

    void hideProgressDialog();

    void showToastError(String message);

}
