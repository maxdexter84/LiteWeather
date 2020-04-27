package com.maxdexter.liteweather.data;

import android.util.Log;

import com.maxdexter.liteweather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class CreateWeather {

    public static ArrayList<DailyWeather> addWeather(JSONObject json) throws JSONException {
        JSONArray arr;
        JSONObject day;
        DailyWeather dailyWeather;
        ArrayList<DailyWeather> weathersList = new ArrayList<>();
        try{

            arr = json.getJSONArray("daily");
            for (int i = 0; i < arr.length(); i++) {
                day = arr.getJSONObject(i);
                JSONObject temp = day.getJSONObject("temp");
                JSONObject feels_like = day.getJSONObject("feels_like");
                JSONObject weather = day.getJSONArray("weather").getJSONObject(0);
                String DT = initDate(day.getLong("dt"));
                String sunrise = initDate(day.getLong("sunrise"));
                String sunset = initDate(day.getLong("sunset"));
                String tempDay = (int) temp.getDouble("day") + "";
                String tempMin = (int) temp.getDouble("min") + "";
                String tempMax = (int) temp.getDouble("max") + "";
                String tempNight = (int) temp.getDouble("night") + "";
                String tempEve = (int) temp.getDouble("eve") + "";
                String tempMorn = (int) temp.getDouble("morn") + "";
                String pressure = (int) day.getDouble("pressure") + " ";
                String humidity = (int) day.getDouble("humidity") + " ";
                String wind_speed = (int) day.getDouble("wind_speed") + " ";
                String description = weather.getString("clear sky");
                int imageResourceId = getWeatherIcon(description);
                dailyWeather = new DailyWeather(DT, sunrise, sunset, tempDay, tempMin, tempMax,
                        tempNight, tempEve, tempMorn, pressure, humidity, wind_speed, description, imageResourceId);
                weathersList.add(dailyWeather);
            }
            return weathersList;
        }catch (Exception e){
            Log.d("Log", "One or more fields not found in the JSON data");
        }
        return null;
    }
    private static String initDate(long date) {
        long currentTime = date * 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MMMM", Locale.getDefault());
        String currentDate = dateFormat.format(currentTime);
        return currentDate;

    }
    private static int getWeatherIcon(String weather){
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
                return icon;
            }
        }
        return icon;
    }
}
