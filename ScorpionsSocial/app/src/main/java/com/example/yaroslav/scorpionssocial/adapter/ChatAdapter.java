package com.example.yaroslav.scorpionssocial.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yaroslav.scorpionssocial.R;
import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.Message;

import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<Message> data;
    private int OWN = 100;

    public ChatAdapter(List<Message> data) {
        this.data = data;
    }

    //*********!!!!*******
    public void addMessage(Message message) {
        data.add(message);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == OWN) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_own, parent, false);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_other, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(data.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = data.get(position);
        if (message.getUserId() == App.getApp().getPreferenceManager().getUser().getId())
            return OWN;
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.chatMessage);
        }
    }


}

