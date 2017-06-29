package com.example.yaroslav.scorpionssocial.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.example.yaroslav.scorpionssocial.R;
import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.AuthorizationResult;
import com.example.yaroslav.scorpionssocial.model.User;
import com.example.yaroslav.scorpionssocial.model.UserRegistrationInfo;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.LoginPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.LoginActivityInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginPresenter extends BasePresenter implements LoginPresenterInterface {

    private final String TAG = "LogTag";

    private LoginActivityInterface view;
    private final UserRegistrationInfo userRegistrationInfo;
    private Resources resources;

    public LoginPresenter(LoginActivityInterface view, Context context) {
        super(context);
        this.view = view;
        userRegistrationInfo = new UserRegistrationInfo();
        resources = context.getResources();
    }

    public void login(String login, String password) {
        if (!validate(login, password))
            return;
        loginRequest(login, password);
    }

    @Override
    public void register() {
        launchRegistrationActivity();
    }


    private boolean validate(String login, String password) {
        boolean isValid = true;

        if (login.isEmpty()) {
            view.showLoginErrorMessage("Некоректний логін");
            isValid = false;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            view.showPasswordErrorMessage("Некоректний пароль");
            isValid = false;
        }

        return isValid;
    }

    private void loginRequest(String login, String password) {
        view.showProgressDialog();
        App.getApi().login(login, password, "password").enqueue(new Callback<AuthorizationResult>() {
            @Override
            public void onResponse(Call<AuthorizationResult> call, Response<AuthorizationResult> response) {
                if (response.isSuccessful()) {
                    userRegistrationInfo.setToken("Bearer " + response.body().getAccess_token());
                    identify();
                } else if (response.code() == 400) {
                    view.hideProgressDialog();
                    view.showToastError("Неправильний логін або пароль");
                } else
                    onFailure(call, new Throwable(response.message()));
            }

            @Override
            public void onFailure(Call<AuthorizationResult> call, Throwable t) {
                view.hideProgressDialog();
                Log.d(TAG, t.getMessage());
                view.showToastError(resources.getString(R.string.error_request));
            }
        });
    }

    private void identify() {
        App.getApi().getUser(userRegistrationInfo.getToken()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                view.hideProgressDialog();
                if (response.isSuccessful()) {
                    int id = response.body().getId();
                    userRegistrationInfo.setId(id);
                    userRegistrationInfo.setEmail(response.body().getEmail());
                    userRegistrationInfo.setName(response.body().getUserName());
                    Log.d("TEST",response.body().getUserName());
                    App.getApp().getPreferenceManager().saveUser(userRegistrationInfo);
                    launchUserActivity(id);
                } else
                    onFailure(call, new Throwable(response.message()));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.hideProgressDialog();
                Log.d(TAG, t.getMessage());
                view.showToastError(resources.getString(R.string.error_request));
            }
        });
    }
}

