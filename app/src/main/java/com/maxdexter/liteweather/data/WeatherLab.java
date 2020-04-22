package com.maxdexter.liteweather.data;

import com.maxdexter.liteweather.R;

import java.util.ArrayList;

public class WeatherLab {
    private ArrayList<Weather> weatherList;

    public ArrayList<Weather> getWeatherList() {
        return weatherList;
    }

    public WeatherLab(){
        weatherList = new ArrayList<>();
        weatherList.add(new Weather("Санкт Петербург","21.04.20","+3","+10","+13","+7", R.drawable.broken_clouds,"Пасмурно"));
        weatherList.add(new Weather("Санкт Петербург","21.04.20","+3","+10","+13","+7", R.drawable.broken_clouds,"Пасмурно"));
        weatherList.add(new Weather("Санкт Петербург","21.04.20","+3","+10","+13","+7", R.drawable.broken_clouds,"Пасмурно"));
        weatherList.add(new Weather("Санкт Петербург","21.04.20","+3","+10","+13","+7", R.drawable.broken_clouds,"Пасмурно"));
        weatherList.add(new Weather("Санкт Петербург","21.04.20","+3","+10","+13","+7", R.drawable.broken_clouds,"Пасмурно"));
    }
}
