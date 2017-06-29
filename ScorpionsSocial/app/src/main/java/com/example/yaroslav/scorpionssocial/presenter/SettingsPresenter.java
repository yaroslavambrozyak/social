package com.example.yaroslav.scorpionssocial.presenter;


import android.content.Context;
import android.util.Log;

import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.PersonalInfo;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.SettingsPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.SettingsActivityInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsPresenter extends BasePresenter implements SettingsPresenterInterface {

    private SettingsActivityInterface view;
    private int id;
    private String token;
    private final String TAG = "SettTag";

    public SettingsPresenter(SettingsActivityInterface view, Context context, int id) {
        super(context);
        this.view = view;
        this.id = id;
        token = App.getApp().getPreferenceManager().getUser().getToken();
    }

    @Override
    public void initSettings() {
        App.getApi().getUserPersonalInfo(id, token).enqueue(new Callback<PersonalInfo>() {
            @Override
            public void onResponse(Call<PersonalInfo> call, Response<PersonalInfo> response) {
                if (response.isSuccessful()) {
                    changeSettings(response.body());
                } else onFailure(call, new Throwable(response.message()));
            }

            @Override
            public void onFailure(Call<PersonalInfo> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    public void setSettings(final PersonalInfo settings) {
        App.getApi().changeUserPersonalInfo(id, token, settings.getBirthDate().toString(), settings.getFirstName(),
                settings.getLastName(), settings.getPhoneNumber()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    changeSettings(settings);
                } else
                    onFailure(call, new Throwable(response.message()));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });
    }

    private void changeSettings(PersonalInfo personalInfo) {
        view.setHintName(personalInfo.getFirstName());
        view.setHintSurName(personalInfo.getLastName());
        view.setHintBirthDay(personalInfo.getBirthDate().toString());
        view.setHintPhone(personalInfo.getPhoneNumber());
    }
}
