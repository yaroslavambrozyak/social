package com.example.yaroslav.scorpionssocial.presenter;


import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.Registration;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.RegistrationPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.RegistrationActivityInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationPresenter extends BasePresenter implements RegistrationPresenterInterface {

    private RegistrationActivityInterface registrationActivity;
    private final String TAG = "RegTag";

    public RegistrationPresenter(RegistrationActivityInterface registrationActivity, Context context) {
        super(context);
        this.registrationActivity = registrationActivity;
    }

    @Override
    public void register(Registration registration) {
        if (!validate(registration))
            return;
        registrationRequest(registration);

    }

    private boolean validate(Registration registration) {
        boolean isValid = true;

        if (registration.getEmail().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(registration.getEmail()).matches()) {
            registrationActivity.showEmailErrorMessage("Некоректний емейл");
            isValid = false;
        }

        if (registration.getLogin().isEmpty()) {
            registrationActivity.showLoginErrorMessage("Неправильний логін");
            isValid = false;
        }

        if (registration.getName().isEmpty()) {
            registrationActivity.showNameErrorMessage("Неправильне ім'я");
            isValid = false;
        }

        if (registration.getSurName().isEmpty()) {
            registrationActivity.showSurNameErrorMessage("Неправильне прізвище");
            isValid = false;
        }

        if (registration.getPassword().length() < 4 || registration.getPassword().length() > 20) {
            registrationActivity.showPasswordErrorMessage("Неправильний пароль");
            isValid = false;
        }

        if (!registration.getConfirmPassword().equals(registration.getPassword()) || registration.getConfirmPassword().isEmpty()) {
            registrationActivity.showPasswordConfirmErrorMessage("Паролі не співпадають");
            isValid = false;
        }

        return isValid;
    }

    private void registrationRequest(final Registration registration) {
        registrationActivity.showProgressDialog();
        App.getApi().createUser(registration.getLogin(), registration.getEmail(),
                registration.getPassword(), registration.getConfirmPassword()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                registrationActivity.hideProgressDialog();
                if (response.isSuccessful()) {
                    registrationActivity.hideProgressDialog();
                    launchLoginActivity();
                } else
                    onFailure(call, new Throwable(response.message()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                registrationActivity.hideProgressDialog();
                Log.d(TAG, t.getMessage());
                registrationActivity.showToastError();
            }
        });
    }
}
