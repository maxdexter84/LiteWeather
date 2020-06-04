package com.maxdexter.liteweather.broadcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.app.NotificationCompat;

import com.maxdexter.liteweather.R;

public class SignalOn extends BroadcastReceiver {
    ConnectivityManager connectivityManager;
    private int messageId = 999;
    @Override
    public void onReceive(Context context, Intent intent) {
        connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        hasConnection(context);
    }

    private void initNotify(Context context,String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"2")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Broadcast Receiver")
                .setContentText(text);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++,builder.build());
    }

    protected void hasConnection(Context context) {
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if(netInfo!= null && netInfo.isConnectedOrConnecting()){
            switch (netInfo.getType()) {
                case ConnectivityManager.TYPE_MOBILE:
                    initNotify(context,"mobile connected");
                    return;
                case ConnectivityManager.TYPE_WIFI:
                    initNotify(context,"WiFi connected");

            }
        }else{
            initNotify(context,"disconnected");
        }

    }
}
