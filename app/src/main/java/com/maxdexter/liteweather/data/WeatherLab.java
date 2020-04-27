package com.maxdexter.liteweather.data;


import java.util.ArrayList;
import java.util.HashMap;


public class WeatherLab {
    private static String place;
    private static Weather mWeather;
    private static ArrayList<DailyWeather> mDailyWeathers;


    public static String getPlace() {
        return place;
    }

    public static Weather getmWeather() {
        return mWeather;
    }

    public static ArrayList<DailyWeather> getmDailyWeathers() {
        return mDailyWeathers;
    }

    public static void setPlace(String place) {
        WeatherLab.place = place;
    }

    public static void setmWeather(Weather mWeather) {
        WeatherLab.mWeather = mWeather;
    }

    public static void setmDailyWeathers(ArrayList<DailyWeather> mDailyWeathers) {
        WeatherLab.mDailyWeathers = mDailyWeathers;
    }

    public WeatherLab() {


    }
}
