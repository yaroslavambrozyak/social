package com.example.yaroslav.scorpionssocial.view;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yaroslav.scorpionssocial.R;
import com.example.yaroslav.scorpionssocial.model.Registration;
import com.example.yaroslav.scorpionssocial.presenter.RegistrationPresenter;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.RegistrationPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.RegistrationActivityInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity implements RegistrationActivityInterface {

    private RegistrationPresenterInterface presenter;
    private ProgressDialog progressDialog;

    @BindView(R.id.edit_email)
    EditText email;
    @BindView(R.id.edit_login)
    EditText login;
    @BindView(R.id.edit_name)
    EditText name;
    @BindView(R.id.edit_surname)
    EditText surName;
    @BindView(R.id.edit_password)
    EditText password;
    @BindView(R.id.edit_password_confirm)
    EditText confirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        presenter = new RegistrationPresenter(this,RegistrationActivity.this);
        ButterKnife.bind(this);
        initProgressDialog();
    }

    @Override
    @OnClick(R.id.sign_button)
    public void onRegistrationButtonClick() {
        String emailText = email.getText().toString();
        String loginText = login.getText().toString();
        String nameText = name.getText().toString();
        String surNameText = surName.getText().toString();
        String passwordText = password.getText().toString();
        String confirmPasswordText = confirmPassword.getText().toString();
        Registration registration = new Registration(emailText,loginText,nameText,surNameText,passwordText,confirmPasswordText);
        presenter.register(registration);
    }

    @Override
    public void showEmailErrorMessage(String message) {
        email.setError(message);
    }

    @Override
    public void showLoginErrorMessage(String message) {
        login.setError(message);
    }

    @Override
    public void showNameErrorMessage(String message) {
        name.setError(message);
    }

    @Override
    public void showSurNameErrorMessage(String message) {
        surName.setError(message);
    }

    @Override
    public void showPasswordErrorMessage(String message) {
        password.setError(message);
    }

    @Override
    public void showPasswordConfirmErrorMessage(String message) {
        confirmPassword.setError(message);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.hide();
    }

    @Override
    public void showToastError() {
        Toast.makeText(this,"Сталася помилка",Toast.LENGTH_LONG).show();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(RegistrationActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Створюємо акаунт...");
    }
}
