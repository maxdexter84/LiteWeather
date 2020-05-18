package com.maxdexter.liteweather.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HistoryBox {
    private HashMap<String,HistoryWeather> mHistoryWeatherList;
    private static HistoryBox sHistoryBox;
    public static HistoryBox get(Context context){
        if(sHistoryBox == null){
            sHistoryBox = new HistoryBox(context);
        }
        return sHistoryBox;
    }

    public void setHistoryWeatherList(HashMap<String,HistoryWeather> historyWeatherList) {
        mHistoryWeatherList = historyWeatherList;
    }

    public HashMap<String,HistoryWeather> getHistoryWeatherList() {
        return mHistoryWeatherList;
    }

    private HistoryBox(Context context){
        mHistoryWeatherList = new HashMap<>();
    }
    public void addList(String cityName,HistoryWeather historyWeather){
        mHistoryWeatherList.put(cityName,historyWeather);
    }
}
