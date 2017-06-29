package com.example.yaroslav.scorpionssocial.presenter.interfaces;


public interface ConversationPresenterInterface extends BaseInterface {
    void initConversation();
    void createConversation(String name);
    void addUserToConversation(int conId, int userId);
}
