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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


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


        initGetToken();
        initNotificationChannel();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBatteryLow);
    }

    private void initGetToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("PushMessage", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                    }
                });
    }

    // инициализация канала нотификаций
    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("2", "name", importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }
}






