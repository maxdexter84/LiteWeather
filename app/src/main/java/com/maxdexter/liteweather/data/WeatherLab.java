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
        weatherList.add(new Weather("Санкт Петербург","21.04.20","+3","+10","+13","+6", R.drawable.broken_clouds,"Пасмурно"));
        weatherList.add(new Weather("Санкт Петербург","22.04.20","+4","+3","+11","+5", R.drawable.clear_sky,"Пасмурно"));
        weatherList.add(new Weather("Санкт Петербург","23.04.20","+6","+5","+9","+3", R.drawable.few_clouds,"Пасмурно"));
        weatherList.add(new Weather("Санкт Петербург","24.04.20","+4","+2","+8","+4", R.drawable.rain,"Пасмурно"));
        weatherList.add(new Weather("Санкт Петербург","25.04.20","+9","+1","+4","+6", R.drawable.mist,"Пасмурно"));
    }
}
