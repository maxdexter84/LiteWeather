package com.maxdexter.liteweather.database;

import android.provider.ContactsContract;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.maxdexter.liteweather.data.DailyWeather;
import com.maxdexter.liteweather.data.HistoryWeather;

import java.util.List;

@Dao
public interface WeatherDao {
    @Query("SELECT * FROM historyweather")
    List<DailyWeather> getAll();
    @Query(("SELECT * FROM historyweather WHERE mCityName = :city"))
    HistoryWeather getByCityName(String city);

    @Insert
    void insert(HistoryWeather historyWeather);

    @Update
    void update(HistoryWeather historyWeather);

    @Delete
    void delete(HistoryWeather historyWeather);
}
