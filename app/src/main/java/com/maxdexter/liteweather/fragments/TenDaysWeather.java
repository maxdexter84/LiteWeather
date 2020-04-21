package com.maxdexter.liteweather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxdexter.liteweather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TenDaysWeather extends Fragment {

    public TenDaysWeather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ten_days_weather, container, false);
    }
}
