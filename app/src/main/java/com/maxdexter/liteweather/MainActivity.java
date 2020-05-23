package com.maxdexter.liteweather;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.maxdexter.liteweather.data.AppCache;
import com.maxdexter.liteweather.data.ParseData;
import com.maxdexter.liteweather.data.WeatherLab;
import com.maxdexter.liteweather.data.WeatherLoader;
import com.maxdexter.liteweather.fragments.HistoryFragment;
import com.maxdexter.liteweather.fragments.InfoFragment;
import com.maxdexter.liteweather.fragments.TenDaysWeather;
import com.maxdexter.liteweather.fragments.TodayWeather;
import com.maxdexter.liteweather.fragments.TomorrowFragment;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Locale;


public class MainActivity extends BaseActivity  {
    Toolbar toolbar;
public static final int SETTING_CODE = 77;
   private LiveData<String> liveData;
    ParseData parseData = new ParseData();
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
        mAppCache = new AppCache(this);
        liveData = WeatherLab.get(this).getData();
        WeatherLab.get(this).setData(mAppCache.getSavedCity());
        searchViewGetText();
        updateWeatherData(mAppCache.getSavedCity());
        initFAB();

    }

    private void initFAB() {
        FloatingActionButton FAB = findViewById(R.id.floating_button_tools);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBottomSheet();
            }
        });
    }

    private void initBottomSheet(){
        View view = LayoutInflater.from(getApplication()).inflate(R.layout.fragment_bottom_dialog,null);
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
            WeatherLab.get(this).setData(query);
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
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
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
        updateWeatherData(mAppCache.getSavedCity());
        mAppCache.saveCity(city);

    }
    //Обновление/загрузка погодных данных
    private void updateWeatherData(final String city) {
        new Thread(new Runnable() {
            @Override
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
                    getCoordinates(json);
                }
            }
        }).start();
    }

    //Получение координат по названию города
    @SuppressLint("DefaultLocale")
    private void getCoordinates(JSONObject json) {
        final String cityName;
        try{
            JSONObject coord = json.getJSONObject("coord");
            final double lat = coord.getDouble("lat");
            final double lon = coord.getDouble("lon");
            cityName = json.getString("name").toUpperCase(Locale.getDefault());
            updateDailyWeatherData(lat,lon,cityName);
        }catch (Exception e){
            Log.d("Log", "One or more fields not found in the JSON data");
        }
    }
    private void updateDailyWeatherData(final double lat, final double lon, final String cityName) {
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
                    try {
                        parseData.addWeather(j,cityName);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }finally {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                initViewPager();
                            }
                        });
                    }
                }
            }



    public class ViewPagerFragment extends FragmentPagerAdapter {
        public ViewPagerFragment(@NonNull FragmentManager fm) {
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
    public void initViewPager() {
        ViewPagerFragment pagerAdapter = new ViewPagerFragment(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.view_pager_id);
        pager.setAdapter(pagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs_fragment);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            liveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String value) {
                    changeCity(value);
                }
            });
        }
    }

}
