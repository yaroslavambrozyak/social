package com.example.yaroslav.scorpionssocial.view.interfaces;


import com.example.yaroslav.scorpionssocial.model.User;

import java.util.List;

public interface FriendsActivityInterface {
    void setUserFriends(List<User> friends);
    void showProgressBar();
    void hideProgressBar();
}
