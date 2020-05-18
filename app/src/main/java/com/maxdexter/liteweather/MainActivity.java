package com.maxdexter.liteweather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.maxdexter.liteweather.data.AppCache;
import com.maxdexter.liteweather.data.DailyWeather;
import com.maxdexter.liteweather.data.HistoryBox;
import com.maxdexter.liteweather.data.HistoryWeather;
import com.maxdexter.liteweather.data.WeatherLab;
import com.maxdexter.liteweather.data.WeatherLoader;
import com.maxdexter.liteweather.fragments.HistoryFragment;
import com.maxdexter.liteweather.fragments.InfoFragment;
import com.maxdexter.liteweather.fragments.PagerFragment;
import com.maxdexter.liteweather.fragments.TenDaysWeather;
import com.maxdexter.liteweather.fragments.TodayWeather;
import com.maxdexter.liteweather.fragments.TomorrowFragment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
public static final int SETTING_CODE = 77;


    private final Handler handler = new Handler();
    AppCache mAppCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if( AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            ConstraintLayout constraints = findViewById(R.id.main_activity);
            constraints.setBackground(getResources().getDrawable(R.drawable.oblaka));
        }
        initToolbar();
        initDrawerLayout();
        initNavView();
        mAppCache = new AppCache(this);
        searchViewGetText();
         updateWeatherData(mAppCache.getSavedCity());

    }

    private void initNavView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open_draw,R.string.nav_close_draw);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
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
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
//Инициализация SearchView  в menu
        initSearchView(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.tools:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivityForResult(intent,SETTING_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SETTING_CODE){
            recreate();
        }
    }

    private void initSearchView(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.onActionViewExpanded();
        searchView.setQueryHint(mAppCache.getSavedCity().toUpperCase());
        EditText editText = searchView.findViewById(R.id.search_src_text);
        editText.setTextSize(24);
        editText.setHintTextColor(getResources().getColor(R.color.black));
        searchView.clearFocus();

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
    public void addWeather(JSONObject json,String cityName) throws JSONException {
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
                historyWeather = new HistoryWeather(cityName,tempDay,DT,description,imageResourceId);
            }
            WeatherLab.get(this).setPlace(cityName);
            WeatherLab.get(this).setDailyWeathers(weathersList);
           HistoryBox.get(this).addList(cityName,historyWeather);

        }catch (Exception e){
            Log.d("Log", "One or more fields not found in the JSON data");
        }finally {

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                initFrag(new PagerFragment());

            }else {
                initLand();
            }

        }
    }
    private void initFrag(Fragment frag){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = frag;
            fm.beginTransaction().add(R.id.fragment_container,fragment).commitAllowingStateLoss();
        }else{
            fragment = frag;
            fm.beginTransaction().replace(R.id.fragment_container,fragment).commitAllowingStateLoss();
        }
    }

    private void initLand(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            FrameLayout fl = findViewById(R.id.recycler_frag);
            if (fl != null){
                FragmentManager fm =getSupportFragmentManager();
                Fragment fragment = fm.findFragmentById(R.id.recycler_frag);
                Fragment fragment1 = fm.findFragmentById(R.id.details_frag);
                if(fragment == null){
                    fragment = new TenDaysWeather();
                    fragment1 = new TodayWeather();
                    fm.beginTransaction().add(R.id.recycler_frag,fragment).commit();
                    fm.beginTransaction().add(R.id.details_frag,fragment1).commit();
                }

            }
       }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.history_box:
                initFrag(new HistoryFragment());
                drawerLayout.closeDrawers();
                return true;
            case R.id.info_box:
                initFrag(new InfoFragment());
                drawerLayout.closeDrawers();
                return true;
            case R.id.home:
                initFrag(new PagerFragment());
                drawerLayout.closeDrawers();
                return true;
        }
        return false;
    }


}
