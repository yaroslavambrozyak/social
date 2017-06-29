package com.example.yaroslav.scorpionssocial.network;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;

import com.example.yaroslav.scorpionssocial.R;
import com.example.yaroslav.scorpionssocial.model.Message;
import com.example.yaroslav.scorpionssocial.view.ChatActivity;
import com.example.yaroslav.scorpionssocial.view.interfaces.UpdateMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import microsoft.aspnet.signalr.client.Connection;
import microsoft.aspnet.signalr.client.Credentials;
import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.Request;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;


public class SignalRService extends Service {

    private String token;
    private ArrayMap<Integer, Connection> connections;
    private Handler handler;
    private final IBinder mBinder = new LocalBinder();
    private UpdateMessage updateMessage;
    private boolean isRunning;

    public SignalRService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SERV","onStart");
       // if (!isRunning) {
            Log.d("SERV","First");
            connections = new ArrayMap<>();
            token = intent.getStringExtra("token");
            startSignalR(intent.getIntegerArrayListExtra("id"));
        //}
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        for (int i = 0; i < connections.size(); i++)
  //          connections.valueAt(i).stop();
    }

    public void sendMessage(Message message) {
        connections.get(message.getConversationId()).send(message);
    }

    private void startSignalR(ArrayList<Integer> conversations) {
        Platform.loadPlatformComponent(new AndroidPlatformComponent());
        isRunning = true;
        for (int i = 0; i < conversations.size(); i++) {
            int conId = conversations.get(i);
            @SuppressLint("DefaultLocale")
            String conversationString = String.format("conversation_id=%d", conId);
            final Connection connection = new Connection("http://localhost:8081/messages_pcon", conversationString);
            connection.setCredentials(new Credentials() {
                @Override
                public void prepareRequest(Request request) {
                    request.addHeader("Authorization", token);
                }
            });
            SignalRFuture<Void> start = connection.start();
            try {
                start.get();
                Log.d("CON", "DONE");
                connections.put(conId, connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            connection.received(new MessageReceivedHandler() {
                @Override
                public void onMessageReceived(JsonElement jsonElement) {
                    Gson gson = new Gson();
                    Message message = gson.fromJson(jsonElement.getAsString(), Message.class);
                    Log.d("MES", message.getText());
                    onReceived(message.getText());
                }
            });
        }
    }

    private void onReceived(String message) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setSmallIcon(R.mipmap.ic_mail_white_24dp);
        notification.setContentTitle("Нове повідомлення");
        notification.setContentText(message);
        notification.setVibrate(new long[]{100,100,100,100,100,100});
        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/raw/notification");
        notification.setSound(uri);
        Intent intent = new Intent(this, ChatActivity.class);
        TaskStackBuilder bilder = TaskStackBuilder.create(this);
        bilder.addParentStack(ChatActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManager notificationManagerCompat = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManagerCompat.notify(0,notification.build());
        updateMessage.updateMessage();
    }

    public class LocalBinder extends Binder {
        public SignalRService getSignalR() {
            return SignalRService.this;
        }
    }

    public void setUpdate(UpdateMessage update){
        updateMessage = update;
    }
}
