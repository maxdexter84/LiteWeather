package com.maxdexter.liteweather;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


import com.google.android.gms.maps.model.LatLng;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.maxdexter.liteweather.broadcast.BaseActivity;

import com.maxdexter.liteweather.data.AppCache;
import com.maxdexter.liteweather.data.HistoryBox;
import com.maxdexter.liteweather.data.HistoryWeather;
import com.maxdexter.liteweather.data.LiveDataController;
import com.maxdexter.liteweather.fragments.HistoryFragment;
import com.maxdexter.liteweather.fragments.InfoFragment;
import com.maxdexter.liteweather.fragments.TenDaysWeather;
import com.maxdexter.liteweather.fragments.TodayWeather;
import com.maxdexter.liteweather.fragments.TomorrowFragment;
import com.maxdexter.liteweather.network.NetworkService;
import com.maxdexter.liteweather.network.ResponsResult;
import com.maxdexter.liteweather.pojo.HelperMethods;
import com.maxdexter.liteweather.pojo.WeatherBox;
import com.maxdexter.liteweather.pojo.coord.CoordRes;

import java.io.IOException;
import java.util.List;


public class MainActivity extends BaseActivity{
    SearchView searchView;
    private static final int PERMISSION_REQUEST_CODE = 10;
    private static final String ACTION_SEND_MSG ="liteweather";
    private static final String NAME_MSG = "msg";
    public static final int FLAG_RECEIVER_INCLUDE_BACKGROUND = 0x01000000;
    private FloatingActionButton coordFAB;
    private static final String TAG = "tag";
    Toolbar toolbar;
    public static final int SETTING_CODE = 77;
    private LiveData<String> liveData;
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
        mAppCache = new AppCache(this);
        liveData = LiveDataController.get(this).getData();
        LiveDataController.get(this).setData(mAppCache.getSavedCity());
        searchViewGetText();
        updateWeatherData(mAppCache.getSavedCity());
        initFAB();
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String value) {
                changeCity(value);
            }
        });

        WeatherBox.getInstance().setListener(new WeatherBox.listener() {
            @Override
            public void getCoordLatLon(double lat, double lon) {
                String latS = "" + lat;
                String lonS = "" + lon;
                NetworkService.getInstance().loadData(latS,lonS,"073f40e104f2129961514beb51a721d2","metric");
            }
        });

    }

    private void initFAB() {
        FloatingActionButton FAB = findViewById(R.id.floating_button_tools);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBottomSheet();
            }
        });
        coordFAB = findViewById(R.id.floating_button_coord);
        coordFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResponsResult.getLiveData().setValue(false);
                requestPemissions();
            }
        });
    }

    private void initBottomSheet(){
        @SuppressLint("InflateParams") View view = LayoutInflater.from(getApplication()).inflate(R.layout.fragment_bottom_dialog,null);
        final BottomSheetDialog bottomDialog = new BottomSheetDialog(MainActivity.this,R.style.BottomSheetDialog);
        bottomDialog.setContentView(view);
        bottomDialog.show();
        view.findViewById(R.id.history_box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryFragment historyFragment = HistoryFragment.newInstance();
                assert getFragmentManager() != null;
                historyFragment.show(getSupportFragmentManager(),"history fragment");
                bottomDialog.setDismissWithAnimation(true);
                bottomDialog.cancel();
            }
        });
        view.findViewById(R.id.info_box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoFragment infoFragment = InfoFragment.newInstance();
                assert getFragmentManager() != null;
                infoFragment.show(getSupportFragmentManager(),"blank fragment");
                bottomDialog.cancel();
            }
        });
        view.findViewById(R.id.settings_box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivityForResult(intent,SETTING_CODE);
                bottomDialog.cancel();
            }
        });
    }

    private void searchViewGetText() {
        //получаем строку поиска из намерения ACTION_SEARCH
        Intent intent  = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            changeCity(query);
            LiveDataController.get(this).setData(query);
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
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.onActionViewExpanded();
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                searchView.setQueryHint(s.toUpperCase());
            }
        });

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
        NetworkService.getInstance().loadCoord(city,"073f40e104f2129961514beb51a721d2","metric");
        getLatLon();
    }


    public void getLatLon(){
        ResponsResult.getLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                boolean load = aBoolean;
                if(aBoolean){
                    initViewPager();
                    HelperMethods helperMethods = new HelperMethods();
                     CoordRes coordRes = WeatherBox.getInstance().getCoordRes();
                   sendIntent(coordRes.getWeather().get(0).getDescription());
                    HistoryWeather historyWeather = new HistoryWeather(coordRes.getName(),
                            (int)coordRes.getMain().getTemp()+"",
                            helperMethods.initDate(coordRes.getDt()),
                            helperMethods.getWeatherIcon(coordRes.getWeather().get(0).getDescription()));
                    if(HistoryBox.get(getApplicationContext()).getHistoryWeather(coordRes.getName()) != null){
                        int id = HistoryBox.get(getApplicationContext()).getHistoryWeather(coordRes.getName()).getId();
                        historyWeather.setId(id);
                        HistoryBox.get(getApplicationContext()).updateHistoryWeather(historyWeather);
                    }else HistoryBox.get(getApplicationContext()).addList(historyWeather);

                }

            }
        });

    }

    public class ViewPagerFragment extends FragmentPagerAdapter {
        ViewPagerFragment(@NonNull FragmentManager fm) {
            super(fm);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new TodayWeather();
                case 1: return new TomorrowFragment();
                case 2: return new TenDaysWeather();
            }
            return null;
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return getResources().getText(R.string.today_button) ;
                case 1:return getResources().getText(R.string.tomorrow_button);
                case 2:return getResources().getText(R.string.week_button);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
    public  void initViewPager() {
        ViewPagerFragment pagerAdapter = new ViewPagerFragment(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.view_pager_id);
        pager.setAdapter(pagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs_fragment);
        tabLayout.setupWithViewPager(pager);
    }
    @SuppressLint("WrongConstant")
    public void sendIntent(String text){
        Intent intent = new Intent();
        // Укажем ACTION по которому будем ловить сообщение
        intent.setAction(ACTION_SEND_MSG);
        // Добавим параметр.
        intent.putExtra(NAME_MSG, text);
        // Указываем флаг поднятия приложения
        // (без него будут получать уведомления только
        // загруженные приложения)
        intent.addFlags(FLAG_RECEIVER_INCLUDE_BACKGROUND);
        // Отправка сообщения
        sendBroadcast(intent);
    }
    /*-------------------------------------------------------------*/
    public void requestPemissions() {
        // Проверим, есть ли Permission’ы, и если их нет, запрашиваем их у
        // пользователя
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)==
                PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED){
            // Запрашиваем координаты
            requestLocation();
        }else {
            // Permission’ов нет, запрашиваем их у пользователя
            requestLocationPermissions();
        }
    }

    private void requestLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        // Получаем менеджер геолокаций
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        // Получаем наиболее подходящий провайдер геолокации по критериям.
        // Но определить, какой провайдер использовать, можно и самостоятельно.
        // В основном используются LocationManager.GPS_PROVIDER или
        // LocationManager.NETWORK_PROVIDER, но можно использовать и
        // LocationManager.PASSIVE_PROVIDER - для получения координат в
        // пассивном режиме

        String provider = locationManager.getBestProvider(criteria,true);
        if(provider != null){
            // Будем получать геоположение через каждые 10 секунд или каждые
            // 10 метров
            locationManager.requestLocationUpdates(provider, 10000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double lat = location.getLatitude(); // Широта
                    String latitude = Double.toString(lat);


                    double lng = location.getLongitude(); // Долгота
                    String longitude = Double.toString(lng);

                    getAddress(lat,lng);
                    String accuracy = Float.toString(location.getAccuracy());   // Точность
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                        NetworkService.getInstance().loadData(latitude,longitude,"073f40e104f2129961514beb51a721d2","metric");

                        }
                    }).start();

                    LatLng currentPosition = new LatLng(lat, lng);
                }



                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }
    // Запрашиваем Permission’ы для геолокации
    private void requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            // Запрашиваем эти два Permission’а у пользователя
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }
    // Результат запроса Permission’а у пользователя:
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {   // Запрошенный нами
            // Permission
            if (grantResults.length == 2 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                // Все препоны пройдены и пермиссия дана
                // Запросим координаты
                requestLocation();
            }
        }
    }


    //Получаем адрес по координатам
    private void getAddress(final double lon,final double lat) {
        final Geocoder geocoder = new Geocoder(this);
        // Поскольку Geocoder работает по интернету, создаём отдельный поток
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Address> addresses = geocoder.getFromLocation(lon,lat,1);
                    searchView.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] addr = addresses.get(0).getAddressLine(0).split(",");
                            String city = addr[2];
                            searchView.setQueryHint(city);
                            mAppCache.saveCity(city);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
