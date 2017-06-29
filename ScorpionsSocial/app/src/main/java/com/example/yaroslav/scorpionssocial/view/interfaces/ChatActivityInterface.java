package com.example.yaroslav.scorpionssocial.view.interfaces;


import com.example.yaroslav.scorpionssocial.model.Message;

import java.util.List;

public interface ChatActivityInterface {
    void setMessage(List<Message> data);
    void showToastError(String message);
}
