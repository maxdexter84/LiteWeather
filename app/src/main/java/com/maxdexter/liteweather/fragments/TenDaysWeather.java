package com.maxdexter.liteweather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxdexter.liteweather.R;
import com.maxdexter.liteweather.adapter.DailyWeatherAdapter;
import com.maxdexter.liteweather.data.WeatherLab;
import com.maxdexter.liteweather.pojo.Daily;
import com.maxdexter.liteweather.pojo.WeatherBox;

import java.util.ArrayList;
import java.util.List;

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
        List<Daily> list = WeatherBox.getInstance().getResult().getDaily();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_ten_days_fragment_id);
        dailyWeatherAdapter = new DailyWeatherAdapter(list);
        recyclerView.setAdapter(dailyWeatherAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }




}
