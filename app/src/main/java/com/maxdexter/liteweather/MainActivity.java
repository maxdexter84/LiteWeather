package com.maxdexter.liteweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.maxdexter.liteweather.fragments.TodayWeather;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentTransaction();
    }

    private void fragmentTransaction() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_main);
        if(fragment == null){
            fragment = new TodayWeather();
            fm.beginTransaction().add(R.id.fragment_container_main,fragment).commit();
        }
    }
}
