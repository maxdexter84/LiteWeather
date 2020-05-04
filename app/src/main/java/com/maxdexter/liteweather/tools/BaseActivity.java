package com.maxdexter.liteweather.tools;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.maxdexter.liteweather.R;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String sNameSharedPreference = "LOGIN";
    //Имя параметра в настройках
    private static final String sISDarkThem = "IS_DARK_THEM";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
            if(isDarkTheme()){
                setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
            } else {
                setTheme(R.style.MyTheme);
            }

    }
    //Чтенеие настроек
    protected boolean isDarkTheme(){
        SharedPreferences sharedPref = getSharedPreferences(sNameSharedPreference, MODE_PRIVATE);
        // Если настройка не найдена , то берется параметр по умолчанию
        return sharedPref.getBoolean(sISDarkThem,true);
    }

    //Сохранение настроек
    protected void setDarkTheme(boolean isDarkTheme){
        SharedPreferences sharedPreferences = getSharedPreferences(sNameSharedPreference,MODE_PRIVATE);
        //Параметры сохраняются посредствам специального класса editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(sISDarkThem,isDarkTheme);
        editor.apply();
    }}
