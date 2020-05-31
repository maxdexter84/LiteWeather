package com.maxdexter.liteweather.data;



import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.maxdexter.liteweather.pojo.Daily;

import java.util.List;


public class WeatherLab {
    private static WeatherLab sWeatherLab;
    private String mPlace;
    private MutableLiveData<String> liveData = new MutableLiveData<>();

   public LiveData<String> getData() {
        return liveData;
    }
    public void setData(String city){
        liveData.setValue(city);
    }
    public void setPlace(String place) {
        mPlace = place;
    }

    public void setDailyWeathers(List<Daily> dailyWeathers) {
        mDailyWeathers = dailyWeathers;
    }

    private List<Daily> mDailyWeathers;

    public String getPlace() {
        return mPlace;
    }

    public Daily getDailyWeather(int position){
        Daily dailyWeather;
        if(mDailyWeathers.get(position) != null){
            dailyWeather = mDailyWeathers.get(position);
            return dailyWeather;
        }
        return null;
    }

    public List<Daily> getDailyWeathers() {
        return mDailyWeathers;
    }

    public WeatherLab(Context context) {

    }


    public static WeatherLab get(Context context){
    if(sWeatherLab == null){
        sWeatherLab = new WeatherLab(context);
    }
        return sWeatherLab;
    }

    public WeatherLab() {
    }
}
