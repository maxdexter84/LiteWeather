package com.maxdexter.liteweather.data;

public class HistoryWeather {
    private String mCityName;
    private String mTempDay;
    private String mDT;
    private String mDescription;
    private int mImageResourceId;

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getTempDay() {
        return mTempDay;
    }

    public void setTempDay(String tempDay) {
        mTempDay = tempDay;
    }

    public String getDT() {
        return mDT;
    }

    public void setDT(String DT) {
        mDT = DT;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        mImageResourceId = imageResourceId;
    }

    public HistoryWeather(String cityName, String tempDay, String DT, String description, int imageResourceId) {
        mCityName = cityName;
        mTempDay = tempDay;
        mDT = DT;
        mDescription = description;
        mImageResourceId = imageResourceId;
    }
}
