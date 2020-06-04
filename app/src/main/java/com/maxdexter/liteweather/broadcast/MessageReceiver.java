package com.maxdexter.liteweather.broadcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.maxdexter.liteweather.R;

public class MessageReceiver extends BroadcastReceiver {


    private int messageId = 0;

    public MessageReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("msg");
        if (message == null) {
            message = "";
        }
        // создать нотификацию
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Broadcast Receiver")
                .setContentText(message);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++, builder.build());
    }
}
