package com.maxdexter.liteweather.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Weather {
    private String mPlace;
    private Date mDate;
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
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

    public static void setWeatherList(Map<String, Weather> weatherList) {
        sWeatherList = weatherList;
    }

    public Weather(String place, Date date, String nightTemp, String dayTemp, String currentTemp, String feelingTemp, int imageResourceId, String weatherDescription) {
       mPlace = place;
        mDate = date;
        mNightTemp = nightTemp;
        mDayTemp = dayTemp;
        mCurrentTemp = currentTemp;
        mFeelingTemp = feelingTemp;
        mImageResourceId = imageResourceId;
        mWeatherDescription = weatherDescription;
        sWeatherList.put(place,new Weather(mPlace,mDate,mNightTemp,mDayTemp,mCurrentTemp,mFeelingTemp,mImageResourceId,mWeatherDescription));
    }
}
