package com.example.yaroslav.scorpionssocial.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yaroslav.scorpionssocial.R;
import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.User;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {


    private List<User> data;
    private OnItemClickListener listener;
    private static Picasso picasso;

    public interface OnItemClickListener {
        void onItemClick(User item);
    }

    public FriendAdapter(List<User> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
        picasso = App.getApp().getPicasso();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_single, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear(){
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0,size);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CircleImageView profileImage;

        public ViewHolder(View itemView) {
            super(itemView);
            profileImage = (CircleImageView) itemView.findViewById(R.id.profile_image);
            textView = (TextView) itemView.findViewById(R.id.testText);
        }

        public void bind(final User item, final OnItemClickListener listener) {
            textView.setText(item.getUserName());
            String url = String.format("http://localhost:8081/api/users/%d/picture", item.getId());
            picasso.load(url).resize(400,200).centerCrop().into(profileImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


}
