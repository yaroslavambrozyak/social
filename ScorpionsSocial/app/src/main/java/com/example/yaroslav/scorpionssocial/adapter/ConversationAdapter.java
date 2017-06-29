package com.example.yaroslav.scorpionssocial.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yaroslav.scorpionssocial.R;
import com.example.yaroslav.scorpionssocial.model.Conversation;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {

    private List<Conversation> data;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void OnItemClick(Conversation conversation);
    }

    public ConversationAdapter(List<Conversation> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_single, parent, false);
        return new ConversationAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.testName);
            textMessage = (TextView) itemView.findViewById(R.id.testMessage);
        }

        public void bind(final Conversation item, final OnItemClickListener listener) {
            textName.setText(item.getName());
            textMessage.setText(item.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(item);
                }
            });
        }
    }
}

