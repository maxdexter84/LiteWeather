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
import android.widget.TextView;
import android.widget.Toast;

import com.maxdexter.liteweather.data.AppCache;
import com.maxdexter.liteweather.data.CreateWeather;
import com.maxdexter.liteweather.data.DailyWeather;
import com.maxdexter.liteweather.data.SearchLab;
import com.maxdexter.liteweather.data.Weather;
import com.maxdexter.liteweather.data.WeatherLab;
import com.maxdexter.liteweather.data.WeatherLoader;
import com.maxdexter.liteweather.fragments.TenDaysWeather;
import com.maxdexter.liteweather.fragments.TodayWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    SearchLab mSearchLab;
    private final Handler handler = new Handler();
//    AppCache mAppCache = new AppCache(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        
       // fragmentTransaction();
        searchViewGetText();


    }

    private void searchViewGetText() {
        //получаем строку поиска из намерения ACTION_SEARCH
        Intent intent  = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            String query = intent.getStringExtra(SearchManager.QUERY);
            changeCity(query);
//           mSearchLab = new SearchLab();
//           mSearchLab.addPrevSearched(query);

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
       // mAppCache.saveCity(city);
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
                                renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void updateDailyWeatherData(final double lat, final double lon) {
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
                                addWeather(j);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }.start();
    }

    //Обработка загруженных данных и обновление UI
    @SuppressLint("DefaultLocale")
    private void renderWeather(JSONObject json) {
        Weather weather;
        String cityName;
        String date = initCurrentDate();
        String currentTemp;
        String nightTemp;
        String dayTemp;
        String feelingTemp;
        String weatherDesc;
        int imageResId;

        try{
            JSONObject coord = json.getJSONObject("coord");
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            int temp =  (int)main.getDouble("temp");
            int min = (int)main.getDouble("temp_min");
            int max = (int)main.getDouble("temp_max");
            int feelTemp = (int)main.getDouble("feels_like");
            double lat = coord.getDouble("lat");
            double lon = coord.getDouble("lon");
            updateDailyWeatherData(lat,lon);
            cityName = json.getString("name").toUpperCase(Locale.getDefault());
            currentTemp = String.format("%d %s",temp," ℃");
            nightTemp = "" + min;
            dayTemp = "" + max;
            feelingTemp = " " + feelTemp;
            weatherDesc = details.getString("description").toUpperCase(Locale.getDefault());
            imageResId = getWeatherIcon(weatherDesc);
            weather = new Weather(date,nightTemp,dayTemp,currentTemp,feelingTemp,imageResId,weatherDesc);
            WeatherLab.setmWeather(weather);
            WeatherLab.setPlace(cityName);
        }catch (Exception e){
            Log.d("Log", "One or more fields not found in the JSON data");
        }


    }
    private String initCurrentDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MMMM", Locale.getDefault());
        String currentDate = dateFormat.format(date);
        return currentDate;

    }
    private static String initDate(long date) {
        long currentTime = date * 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MMMM", Locale.getDefault());
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
    public void addWeather(JSONObject json) throws JSONException {
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
                String description = weather.getString("description");
                int imageResourceId = getWeatherIcon(description);
                dailyWeather = new DailyWeather(DT, sunrise, sunset, tempDay, tempMin, tempMax,
                        tempNight, tempEve, tempMorn, pressure, humidity, wind_speed, description, imageResourceId);
                weathersList.add(dailyWeather);
            }
            WeatherLab.setmDailyWeathers(weathersList);
        }catch (Exception e){
            Log.d("Log", "One or more fields not found in the JSON data");
        }finally {
            fragmentTransaction();
        }

    }


}
