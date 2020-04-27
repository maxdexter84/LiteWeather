package com.maxdexter.liteweather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxdexter.liteweather.R;
import com.maxdexter.liteweather.adapter.DailyWeatherAdapter;
import com.maxdexter.liteweather.adapter.WeatherAdapter;
import com.maxdexter.liteweather.data.DailyWeather;
import com.maxdexter.liteweather.data.Weather;
import com.maxdexter.liteweather.data.WeatherLab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TenDaysWeather extends Fragment {

    DailyWeatherAdapter dailyWeatherAdapter;

    public TenDaysWeather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ten_days_weather, container, false);

        ArrayList<DailyWeather> value =WeatherLab.getmDailyWeathers();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_ten_days_fragment_id);
       dailyWeatherAdapter = new DailyWeatherAdapter(value);
        recyclerView.setAdapter(dailyWeatherAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterListener();
        return view;
    }

    private void adapterListener() {
        dailyWeatherAdapter.setListener(new DailyWeatherAdapter.Listener() {
            @Override
            public void onClick(int position) {
                
            }
        });
    }

    private void createInitAdapter(View view) {
        if(WeatherLab.getmDailyWeathers() != null){
            ArrayList<DailyWeather> value =WeatherLab.getmDailyWeathers();
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view_ten_days_fragment_id);
            DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(value);
            recyclerView.setAdapter(dailyWeatherAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            Log.d("TAG","WeatherLab.getWeatherLab() == null");
        }





    }
}
