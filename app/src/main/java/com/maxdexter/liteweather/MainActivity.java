package com.maxdexter.liteweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;

import com.maxdexter.liteweather.fragments.TenDaysWeather;
import com.maxdexter.liteweather.fragments.TodayWeather;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        
        fragmentTransaction();

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
            return super.onCreateOptionsMenu(menu);
    }
}
