package com.maxdexter.liteweather;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

import com.maxdexter.liteweather.data.AppCache;
import com.maxdexter.liteweather.data.DailyWeather;
import com.maxdexter.liteweather.data.WeatherLab;
import com.maxdexter.liteweather.data.WeatherLoader;
import com.maxdexter.liteweather.fragments.TenDaysWeather;
import com.maxdexter.liteweather.fragments.TodayWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    private final Handler handler = new Handler();
    AppCache mAppCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        mAppCache = new AppCache(this);
        searchViewGetText();

      updateWeatherData(mAppCache.getSavedCity());


    }

    private void searchViewGetText() {
        //получаем строку поиска из намерения ACTION_SEARCH
        Intent intent  = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            String query = intent.getStringExtra(SearchManager.QUERY);
            changeCity(query);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
            MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);

        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void fragmentTransaction() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_main);
        Fragment listFragment = fm.findFragmentById(R.id.fragment_container_list);
        if(fragment == null){
            fragment = new TodayWeather();
            fm.beginTransaction().add(R.id.fragment_container_main,fragment).commit();
        }else fm.beginTransaction().replace(R.id.fragment_container_main,fragment).commit();
        if(listFragment == null){
            listFragment = new TenDaysWeather();
            fm.beginTransaction().add(R.id.fragment_container_list,listFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
//Инициализация SearchView  в menu
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
// Здесь можно указать будет ли строка поиска изначально развернута или свернута в значок
        searchView.setIconifiedByDefault(true);

        return true;
    }

    //Обновляем вид, сохраняем выбранный город
    public void changeCity(String city) {
        updateWeatherData(city);
        mAppCache.saveCity(city);
    }
    //Обновление/загрузка погодных данных
    private void updateWeatherData(final String city) {
        new Thread() {//Отдельный поток для запроса на сервер
            public void run() {
                final JSONObject json = WeatherLoader.getData(city);
                // Вызов методов напрямую может вызвать runtime error
                if (json == null) {
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Место не найдено",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                                getCoordinates(json);
                        }
                    });
                }
            }
        }.start();
    }
    private void updateDailyWeatherData(final double lat, final double lon, final String cityName) {
        new Thread() {//Отдельный поток для запроса на сервер
            public void run() {
                final JSONObject j = WeatherLoader.getDataSevenDays(lat,lon);
                // Вызов методов напрямую может вызвать runtime error
                if (j == null) {
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Место не найдено",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            try {
                                addWeather(j,cityName);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }.start();
    }

    //Получение координат по названию города
    @SuppressLint("DefaultLocale")
    private void getCoordinates(JSONObject json) {
        String cityName;
        try{
            JSONObject coord = json.getJSONObject("coord");
            double lat = coord.getDouble("lat");
            double lon = coord.getDouble("lon");
            cityName = json.getString("name").toUpperCase(Locale.getDefault());
            updateDailyWeatherData(lat,lon,cityName);

        }catch (Exception e){
            Log.d("Log", "One or more fields not found in the JSON data");
        }
    }

    private static String initDate(long date) {
        long currentTime = date * 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM HH:mm", Locale.forLanguageTag("ru"));
        String currentDate = dateFormat.format(currentTime);
        return currentDate;

    }
    private  int getWeatherIcon(String weather){
        int icon = R.drawable.clear_sky;
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
    public void addWeather(JSONObject json,String cityName) throws JSONException {
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
                String feeling = (int)feels_like.getDouble("day") + "";
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
                String description = weather.getString("description");
                int imageResourceId = getWeatherIcon(description);
                dailyWeather = new DailyWeather(feeling,DT, sunrise, sunset, tempDay, tempMin, tempMax,
                        tempNight, tempEve, tempMorn, pressure, humidity, wind_speed, description, imageResourceId);
                weathersList.add(dailyWeather);
            }
            WeatherLab.get(this).setPlace(cityName);
            WeatherLab.get(this).setDailyWeathers(weathersList);
        }catch (Exception e){
            Log.d("Log", "One or more fields not found in the JSON data");
        }finally {
            fragmentTransaction();
        }

    }
}
