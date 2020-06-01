package com.maxdexter.liteweather.data;

import android.content.Context;

import androidx.room.Room;

import com.maxdexter.liteweather.database.AppDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HistoryBox {
    //private HashMap<String,HistoryWeather> mHistoryWeatherList;
    private static HistoryBox sHistoryBox;
    private AppDatabase database;
    public static HistoryBox get(Context context){
        if(sHistoryBox == null){
            sHistoryBox = new HistoryBox(context);
        }
        return sHistoryBox;
    }

    public List<HistoryWeather> getHistoryWeatherList() {
        return database.mWeatherDao().getAll();
    }
    public HistoryWeather getHistoryWeather(String city) {
        return database.mWeatherDao().getByCityName(city);
    }

    private HistoryBox(Context context){
        database = Room.databaseBuilder(context, AppDatabase.class, "historyDatabase")
                .allowMainThreadQueries()
                .build();
    }
    public void addList(HistoryWeather historyWeather){
        database.mWeatherDao().insert(historyWeather);
        getHistoryWeatherList();
    }
    public void updateHistoryWeather(HistoryWeather historyWeather){
        database.mWeatherDao().update(historyWeather);
        getHistoryWeatherList();
    }
    public void deleteHistoryWeather(HistoryWeather historyWeather){
        database.mWeatherDao().delete(historyWeather);
        getHistoryWeatherList();
    }
}
