package com.example.yaroslav.scorpionssocial.view.interfaces;


public interface RegistrationActivityInterface {

    void onRegistrationButtonClick();

    void showEmailErrorMessage(String message);

    void showLoginErrorMessage(String message);

    void showNameErrorMessage(String message);

    void showSurNameErrorMessage(String message);

    void showPasswordErrorMessage(String message);

    void showPasswordConfirmErrorMessage(String message);

    void showProgressDialog();

    void hideProgressDialog();

    void showToastError();
}
