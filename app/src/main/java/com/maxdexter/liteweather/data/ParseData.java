package com.maxdexter.liteweather.data;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.maxdexter.liteweather.BaseActivity;
import com.maxdexter.liteweather.MainActivity;
import com.maxdexter.liteweather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

 public class ParseData extends BaseActivity {

    public ParseData(){
    }


    public  void addWeather(JSONObject json, String cityName) throws JSONException {
        JSONArray arr;
        JSONObject day;
        DailyWeather dailyWeather;
        HistoryWeather historyWeather = null;
        ArrayList<DailyWeather> weathersList = new ArrayList<>();
        try{
            arr = json.getJSONArray("daily");
            for (int i = 0; i < arr.length(); i++) {
                day = arr.getJSONObject(i);
                JSONObject temp = day.getJSONObject("temp");
                JSONObject feels_like = day.getJSONObject("feels_like");
                JSONObject weather = day.getJSONArray("weather").getJSONObject(0);
                String feeling = (int)feels_like.getDouble("day") + "";
                String DT = initDate(day.getLong("dt"));
                String sunrise = initTime(day.getLong("sunrise"));
                String sunset = initTime(day.getLong("sunset"));
                String tempDay = (int) temp.getDouble("day") + "";
                String tempMin = (int) temp.getDouble("min") + "";
                String tempMax = (int) temp.getDouble("max") + "";
                String tempNight = (int) temp.getDouble("night") + "";
                String tempEve = (int) temp.getDouble("eve") + "";
                String tempMorn = (int) temp.getDouble("morn") + "";
                String pressure = (int) day.getDouble("pressure") + " ";
                String humidity = (int) day.getDouble("humidity") + " ";
                String wind_speed = (int) day.getDouble("wind_speed") + " ";
                String description = weather.getString("description");
                int imageResourceId = getWeatherIcon(description);
                dailyWeather = new DailyWeather(feeling,DT, sunrise, sunset, tempDay, tempMin, tempMax,
                        tempNight, tempEve, tempMorn, pressure, humidity, wind_speed, description, imageResourceId);
                weathersList.add(dailyWeather);
                if(historyWeather == null)historyWeather = new HistoryWeather(cityName,tempDay,DT,description,imageResourceId);

            }
            WeatherLab.get(this).setPlace(cityName);
            WeatherLab.get(this).setDailyWeathers(weathersList);

            if(HistoryBox.get(this).getHistoryWeather(cityName) != null){
                int id = HistoryBox.get(this).getHistoryWeather(cityName).getId();
                assert historyWeather != null;
                historyWeather.setId(id);
                HistoryBox.get(this).updateHistoryWeather(historyWeather);
            }else HistoryBox.get(this).addList(historyWeather);


        }catch (Exception e){
            Log.d("Log", "One or more fields not found in the JSON data");
        }
    }
    private static String initDate(long date) {
        long currentTime = date * 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM ", Locale.getDefault());
        return dateFormat.format(currentTime);
    }
    private static String initTime(long date) {
        long current = date * 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.forLanguageTag("ru"));
        return dateFormat.format(current);
    }
    private  int getWeatherIcon(String weather){
        int icon = R.drawable.scattered_clouds;
        HashMap<String, Integer> weatherIcon = new HashMap<>();
        weatherIcon.put("clear sky", R.drawable.clear_sky);
        weatherIcon.put("few clouds",R.drawable.few_clouds);
        weatherIcon.put("scattered clouds",R.drawable.scattered_clouds);
        weatherIcon.put("broken clouds",R.drawable.broken_clouds);
        weatherIcon.put("overcast clouds",R.drawable.scattered_clouds);
        weatherIcon.put("shower rain",R.drawable.shower_rain);
        weatherIcon.put("thunderstorm",R.drawable.thunderstorm);
        weatherIcon.put("thunderstorm with light rain",R.drawable.thunderstorm);
        weatherIcon.put("thunderstorm with rain",R.drawable.thunderstorm);
        weatherIcon.put("heavy thunderstorm",R.drawable.thunderstorm);
        weatherIcon.put("light thunderstorm",R.drawable.thunderstorm);
        weatherIcon.put("rain",R.drawable.rain);
        weatherIcon.put("light rain",R.drawable.rain);
        weatherIcon.put("moderate rain",R.drawable.rain);
        weatherIcon.put("heavy intensity rain",R.drawable.rain);
        weatherIcon.put("snow",R.drawable.snow);
        weatherIcon.put("light snow",R.drawable.snow);
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
