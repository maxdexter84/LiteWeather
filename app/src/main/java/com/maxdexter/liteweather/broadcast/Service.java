package com.maxdexter.liteweather.broadcast;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;


public class Service extends BaseActivity {
    private static final String ACTION_SEND_MSG ="liteweather";
    private static final String NAME_MSG = "msg";
    public static final int FLAG_RECEIVER_INCLUDE_BACKGROUND = 0x01000000;
    IntentFilter mIntentFilter;
    BatteryLow mBatteryLow;
    SignalOn mSignalOn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBatteryLow = new BatteryLow();
        mSignalOn = new SignalOn();
        // Программная регистрация ресивера
        registerReceiver(mBatteryLow, new IntentFilter(Intent.ACTION_BATTERY_LOW));

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mSignalOn,new IntentFilter(NETWORK_STATS_SERVICE));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBatteryLow);
    }



}
