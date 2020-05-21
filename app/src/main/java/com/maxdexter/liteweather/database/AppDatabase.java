package com.maxdexter.liteweather.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.maxdexter.liteweather.data.HistoryWeather;

@Database(entities = {HistoryWeather.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WeatherDao mWeatherDao();
}
