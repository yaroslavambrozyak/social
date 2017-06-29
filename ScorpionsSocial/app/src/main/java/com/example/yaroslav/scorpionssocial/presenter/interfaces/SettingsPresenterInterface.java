package com.example.yaroslav.scorpionssocial.presenter.interfaces;


import com.example.yaroslav.scorpionssocial.model.PersonalInfo;

public interface SettingsPresenterInterface extends BaseInterface {
    void initSettings();
    void setSettings(PersonalInfo personalInfo);
}
