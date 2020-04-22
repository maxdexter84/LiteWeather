package com.maxdexter.liteweather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxdexter.liteweather.R;
import com.maxdexter.liteweather.adapter.WeatherAdapter;
import com.maxdexter.liteweather.data.Weather;
import com.maxdexter.liteweather.data.WeatherLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class TenDaysWeather extends Fragment {
    private WeatherAdapter weatherAdapter;
    public TenDaysWeather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ten_days_weather, container, false);
        createInitAdapter(view);
        weatherAdapter.setListener(new WeatherAdapter.Listener() {
            @Override
            public void onClick(int position) {
                assert getFragmentManager() != null;
                TodayWeather todayWeather =(TodayWeather) getFragmentManager().findFragmentById(R.id.fragment_container_main);
                assert todayWeather != null;
                todayWeather.setContent(position);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_main,todayWeather);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();


            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void createInitAdapter(View view) {
        WeatherLab weatherLab = new WeatherLab();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_ten_days_fragment_id);
        weatherAdapter = new WeatherAdapter(weatherLab.getWeatherList());
        recyclerView.setAdapter(weatherAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
