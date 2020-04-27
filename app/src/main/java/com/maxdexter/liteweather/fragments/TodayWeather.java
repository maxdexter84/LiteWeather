package com.maxdexter.liteweather.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxdexter.liteweather.MainActivity;
import com.maxdexter.liteweather.R;
import com.maxdexter.liteweather.adapter.WeatherAdapter;
import com.maxdexter.liteweather.data.DailyWeather;
import com.maxdexter.liteweather.data.Weather;
import com.maxdexter.liteweather.data.WeatherLab;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWeather extends Fragment{
    private WeatherLab weatherLab;
    private int defaultPosition = 0;
    private TextView mCityName;
    private TextView mCurrentTemp;
    private TextView mFeelingTemp;
    private ImageView mImageWeather;
    private TextView mDateTime;
    private TextView mDayNightTemp;
    private TextView mWeatherDescript;
    public TodayWeather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_today_weather, container, false);
        init(view);
        setContent(defaultPosition);
        return view;
    }

    private void init(View view){
        mCityName = view.findViewById(R.id.city_name_today_fragment_id);
        mCurrentTemp = view.findViewById(R.id.current_temp_text_view_id);
        mFeelingTemp = view.findViewById(R.id.feeling_temp_text_view_id);
        mImageWeather = view.findViewById(R.id.image_weather_image_id);
        mDateTime = view.findViewById(R.id.date_time_current_text_view_id);
        mDayNightTemp = view.findViewById(R.id.day_night_text_view_id);
        mWeatherDescript = view.findViewById(R.id.weather_description_text_view_id);
    }

    @SuppressLint("SetTextI18n")
   protected void setContent(int position){
        Weather weather;
        if(WeatherLab.getmWeather() != null){
            weather =  WeatherLab.getmWeather();;
            int feel = R.string.feeling_by;
            mCityName.setText(WeatherLab.getPlace());
            mCurrentTemp.setText(weather.getCurrentTemp());
            mFeelingTemp.setText("Ощущается как " + weather.getFeelingTemp());
            mImageWeather.setImageResource(weather.getImageResourceId());
            mDateTime.setText(weather.getDate());
            mDayNightTemp.setText("" + weather.getNightTemp() + " " + weather.getDayTemp());
            mWeatherDescript.setText(weather.getWeatherDescription());
        }


    }





}
