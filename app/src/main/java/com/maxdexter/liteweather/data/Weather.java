package com.maxdexter.liteweather.data;

import com.maxdexter.liteweather.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Weather {
    private String mPlace;
    private String mDate;
    private String mNightTemp;
    private String mDayTemp;
    private String mCurrentTemp;
    private String mFeelingTemp;
    private int mImageResourceId;
    private String mWeatherDescription;
    private static Map<String, Weather> sWeatherList = new HashMap<>();

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(Date date) {

    }

    public String getNightTemp() {
        return mNightTemp;
    }

    public void setNightTemp(String nightTemp) {
        mNightTemp = nightTemp;
    }

    public String getDayTemp() {
        return mDayTemp;
    }

    public void setDayTemp(String dayTemp) {
        mDayTemp = dayTemp;
    }

    public String getCurrentTemp() {
        return mCurrentTemp;
    }

    public void setCurrentTemp(String currentTemp) {
        mCurrentTemp = currentTemp;
    }

    public String getFeelingTemp() {
        return mFeelingTemp;
    }

    public void setFeelingTemp(String feelingTemp) {
        mFeelingTemp = feelingTemp;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        mImageResourceId = imageResourceId;
    }

    public String getWeatherDescription() {
        return mWeatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        mWeatherDescription = weatherDescription;
    }

    public static Map<String, Weather> getWeatherList() {
        return sWeatherList;
    }


    public Weather(String place, String date, String nightTemp, String dayTemp, String currentTemp, String feelingTemp, int imageResourceId, String weatherDescription) {
       mPlace = place;
        mDate = date;
        mNightTemp = nightTemp;
        mDayTemp = dayTemp;
        mCurrentTemp = currentTemp;
        mFeelingTemp = feelingTemp;
        mImageResourceId = imageResourceId;
        mWeatherDescription = weatherDescription;
    }

    private int getWeatherIcon(String weather){
        int icon = R.drawable.clear_sky;
        HashMap<String, Integer> weatherIcon = new HashMap<>();
        weatherIcon.put("clear sky", R.drawable.clear_sky);
        weatherIcon.put("few clouds",R.drawable.few_clouds);
        weatherIcon.put("scattered clouds",R.drawable.scattered_clouds);
        weatherIcon.put("broken clouds",R.drawable.broken_clouds);
        weatherIcon.put("shower rain",R.drawable.shower_rain);
        weatherIcon.put("thunderstorm",R.drawable.thunderstorm);
        weatherIcon.put("rain",R.drawable.rain);
        weatherIcon.put("snow",R.drawable.snow);
        weatherIcon.put("mist",R.drawable.mist);
        for(Map.Entry<String, Integer>pair: weatherIcon.entrySet()){
            String key = pair.getKey();
            int value = pair.getValue();
            if (key.equals(weather)){
                icon = value;
            }
        }
        return icon;
    }
}
