package com.maxdexter.liteweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.maxdexter.liteweather.R;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String sNameSharedPreference = "LOGIN";
    //Имя параметра в настройках
    private static final String IsDarkThem = "IS_DARK_THEM";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isDarkTheme()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.MyThemeTransparent);

        }
    }
    //Чтенеие настроек
    protected boolean isDarkTheme(){
        SharedPreferences sharedPref = getSharedPreferences(sNameSharedPreference, MODE_PRIVATE);
        // Если настройка не найдена , то берется параметр по умолчанию
        return sharedPref.getBoolean(IsDarkThem,true);
    }

    //Сохранение настроек
    protected void setDarkTheme(boolean isDarkTheme){
        SharedPreferences sharedPreferences = getSharedPreferences(sNameSharedPreference,MODE_PRIVATE);
        //Параметры сохраняются посредствам специального класса editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IsDarkThem,isDarkTheme);
        editor.apply();
    }

}
