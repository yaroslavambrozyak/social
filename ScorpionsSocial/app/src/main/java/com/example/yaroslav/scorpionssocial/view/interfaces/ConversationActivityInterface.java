package com.example.yaroslav.scorpionssocial.view.interfaces;


import com.example.yaroslav.scorpionssocial.model.Conversation;

import java.util.List;

public interface ConversationActivityInterface {
    void setConversations(List<Conversation> conversations);
    void clearRecycle();
}
