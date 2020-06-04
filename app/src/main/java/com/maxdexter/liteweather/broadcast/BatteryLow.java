package com.maxdexter.liteweather.broadcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.maxdexter.liteweather.R;

public class BatteryLow extends BroadcastReceiver {
    private int messageId = 1000;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"2")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Broadcast Receiver")
                .setContentText(String.valueOf(R.string.battery_low));
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++,builder.build());
    }
}
