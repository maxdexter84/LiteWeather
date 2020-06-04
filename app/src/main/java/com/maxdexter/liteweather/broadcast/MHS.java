package com.maxdexter.liteweather.broadcast;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.maxdexter.liteweather.R;

import java.util.Objects;

public class MHS extends FirebaseMessagingService {
    private int messageId = 0;

    public MHS() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("PushMessage", Objects.requireNonNull(remoteMessage.getNotification().getBody()));
        String title = remoteMessage.getNotification().getTitle();
        if (title == null){
            title = "Push Message";
        }
        String text = remoteMessage.getNotification().getBody();
        // создать нотификацию
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text);
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(messageId++, builder.build());
    }

    @Override
    public void onNewToken(String token) {

        // Если надо посылать сообщения этому экземпляру приложения
        // или управлять подписками приложения на стороне сервера,
        // сохраните этот токен в базе данных. отправьте этот токен вашему
        Log.d("PushMessage", "Token " + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
    }
}


