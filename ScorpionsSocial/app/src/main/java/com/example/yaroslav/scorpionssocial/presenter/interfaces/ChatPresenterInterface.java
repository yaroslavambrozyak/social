package com.example.yaroslav.scorpionssocial.presenter.interfaces;


import com.example.yaroslav.scorpionssocial.model.Message;

public interface ChatPresenterInterface extends BaseInterface {
    void initMessage();
    void sendMessage(Message message);
    void unBindSignalR();
}
