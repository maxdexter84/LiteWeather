package com.maxdexter.liteweather.broadcast;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class Service extends BaseActivity {
    BatteryLow mBatteryLow;
    SignalOn mSignalOn;
    @RequiresApi(api = Build.VERSION_CODES.O)
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
                            Toast.makeText(Service.this, "", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Toast.makeText(Service.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // инициализация канала нотификаций
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initNotificationChannel() {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("2", "name", importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
    }



}






