package com.maxdexter.liteweather;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.maxdexter.liteweather.BaseActivity;


public class Service extends BaseActivity {
    private static final String ACTION_SEND_MSG ="liteweather";
    private static final String NAME_MSG = "msg";
    public static final int FLAG_RECEIVER_INCLUDE_BACKGROUND = 0x01000000;
    BatteryLow mBatteryLow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBatteryLow = new BatteryLow();
        // Программная регистрация ресивера
        registerReceiver(mBatteryLow, new IntentFilter(Intent.ACTION_BATTERY_LOW));
        initNotificationService();
    }

    private void initNotificationService() {
        //  Для формирования канала для нотификации, его надо инициализировать в активити.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("2","name",importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBatteryLow);
    }



}
