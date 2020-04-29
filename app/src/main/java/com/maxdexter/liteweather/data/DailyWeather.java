package com.maxdexter.liteweather.data;

import java.io.Serializable;


public class DailyWeather implements Serializable {
    private String mFeeling;
    private String mDT;
    private String mSunrise;
    private String mSunset;
    private String mTempDay;
    private String mTempMin;
    private String mTempMax;
    private String mTempNight;
    private String mTempEve;
    private String mTempMorn;
    private String mPressure;
    private String mHumidity;
    private String mWind_speed;
    private String mDescription;
    private int mImageResourceId;

    public String getFeeling() {
        return mFeeling;
    }

    public DailyWeather(String feeling, String DT, String sunrise, String sunset, String tempDay, String tempMin, String tempMax, String tempNight, String tempEve, String tempMorn, String pressure, String humidity, String wind_speed, String description, int imageResourceId) {
        mFeeling = feeling;
        mDT = DT;
        mSunrise = sunrise;
        mSunset = sunset;
        mTempDay = tempDay;
        mTempMin = tempMin;
        mTempMax = tempMax;
        mTempNight = tempNight;
        mTempEve = tempEve;
        mTempMorn = tempMorn;
        mPressure = pressure;
        mHumidity = humidity;
        mWind_speed = wind_speed;
        mDescription = description;
        mImageResourceId = imageResourceId;
    }

    public String getDT() {
        return mDT;
    }

    public String getSunrise() {
        return mSunrise;
    }

    public String getSunset() {
        return mSunset;
    }

    public String getTempDay() {
        return mTempDay;
    }

    public String getTempMin() {
        return mTempMin;
    }

    public String getTempMax() {
        return mTempMax;
    }

    public String getTempNight() {
        return mTempNight;
    }

    public String getTempEve() {
        return mTempEve;
    }

    public String getTempMorn() {
        return mTempMorn;
    }

    public String getPressure() {
        return mPressure;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public String getWind_speed() {
        return mWind_speed;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

}
