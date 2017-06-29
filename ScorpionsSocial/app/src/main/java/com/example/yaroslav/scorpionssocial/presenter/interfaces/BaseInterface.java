package com.example.yaroslav.scorpionssocial.presenter.interfaces;


import com.example.yaroslav.scorpionssocial.model.Message;

public interface BaseInterface {
    void launchLoginActivity();

    void launchUserActivity(int id);

    void launchRegistrationActivity();

    void launchSettingsActivity();

    void launchFriendsActivity(int id);

    void launchConversationActivity();

    void launchChatActivity(int id);

    void logout();

}
