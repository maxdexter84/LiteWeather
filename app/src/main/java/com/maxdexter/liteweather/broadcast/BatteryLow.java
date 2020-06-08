package com.maxdexter.liteweather.broadcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.maxdexter.liteweather.R;

public class BatteryLow extends BroadcastReceiver {
    private static final String CHANNEL_ID = "battery" ;
    private int messageId = 100000;
    @Override
    public void onReceive(Context context, Intent intent) {
       int battery = intent.getIntExtra("level",0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"2")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Broadcast Receiver")
                .setContentText("battery charge " + battery);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++,builder.build());

    }



}