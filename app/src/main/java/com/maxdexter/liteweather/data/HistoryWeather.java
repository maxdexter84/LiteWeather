package com.maxdexter.liteweather.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HistoryWeather {
    public int getId() {
        return id;
    }

    public HistoryWeather(String cityName, String tempDay, String DT, String description, int imageResourceId) {
        mCityName = cityName;
        mTempDay = tempDay;
        mDT = DT;
        mDescription = description;
        mImageResourceId = imageResourceId;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mCityName;
    private String mTempDay;
    private String mDT;
    private String mDescription;
    private int mImageResourceId;

    public HistoryWeather() {
    }

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


}
