package com.example.yaroslav.scorpionssocial.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yaroslav.scorpionssocial.R;
import com.example.yaroslav.scorpionssocial.presenter.LoginPresenter;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.LoginPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.LoginActivityInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginActivityInterface {

    @BindView(R.id.edit_login)
    EditText login;
    @BindView(R.id.edit_password)
    EditText password;

    private LoginPresenterInterface presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this,LoginActivity.this);
        ButterKnife.bind(this);
        initProgressBar();
    }

    @Override
    @OnClick(R.id.login_button)
    public void onLoginButtonClick() {
        String loginText = login.getText().toString();
        String passwordText = password.getText().toString();
        presenter.login(loginText,passwordText);
    }

    @Override
    @OnClick(R.id.sign_button)
    public void onRegistrationButtonClick() {
        presenter.register();
    }

    @Override
    public void showLoginErrorMessage(String message) {
        login.setError(message);
    }

    @Override
    public void showPasswordErrorMessage(String message) {
        password.setError(message);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showToastError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void initProgressBar(){
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Аутентифікація...");
    }
}
