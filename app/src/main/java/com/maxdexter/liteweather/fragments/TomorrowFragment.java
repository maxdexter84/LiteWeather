package com.maxdexter.liteweather.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxdexter.liteweather.R;
import com.maxdexter.liteweather.data.DailyWeather;
import com.maxdexter.liteweather.data.WeatherLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class TomorrowFragment extends Fragment {

    private int defaultPosition = 1;
    private TextView mCurrentTemp;
    private TextView mFeelingTemp;
    private ImageView mImageWeather;
    private TextView mDateTime;
    private TextView mDayNightTemp;
    private TextView mWeatherDescript;
    private TextView mWindSpeed;
    private TextView mHumidity;
    private TextView mPressure;
    private TextView mTempMin;
    private TextView mTempMax;
    private TextView mSunriseSunset;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_weather, container, false);
        init(view);
        setContent(defaultPosition);
        return view;
    }

    private void init(View view){

        mCurrentTemp = view.findViewById(R.id.current_temp_text_view_id);
        mFeelingTemp = view.findViewById(R.id.feeling_temp_text_view_id);
        mImageWeather = view.findViewById(R.id.image_weather_image_id);
        mDateTime = view.findViewById(R.id.date_time_current_text_view_id);
        mDayNightTemp = view.findViewById(R.id.day_night_text_view_id);
        mWeatherDescript = view.findViewById(R.id.weather_description_text_view_id);
        mWindSpeed = view.findViewById(R.id.wind_title_id);
        mHumidity = view.findViewById(R.id.humidity_title_id);
        mPressure = view.findViewById(R.id.pressure_title_id);
        mTempMax = view.findViewById(R.id.title_max_id);
        mTempMin = view.findViewById(R.id.title_min_id);
        mSunriseSunset = view.findViewById(R.id.feeling_title_id);
    }

    @SuppressLint("SetTextI18n")
    private void setContent(int position){
        DailyWeather dailyWeather = WeatherLab.get(getContext()).getDailyWeather(position);
        String city = WeatherLab.get(getContext()).getPlace();
        if(dailyWeather != null ){
            int feel = R.string.feeling_by;
            mCurrentTemp.setText(dailyWeather.getTempDay() + " " + getString(R.string.temp_metric));
            mFeelingTemp.setText(getString(R.string.feeling) +" " + dailyWeather.getFeeling() + getString(R.string.temp_metric));
            mImageWeather.setImageResource(dailyWeather.getImageResourceId());
            mDateTime.setText(dailyWeather.getDT());
            mDayNightTemp.setText(getString(R.string.min_temp) +" "+ dailyWeather.getTempMin()+" " +getString(R.string.temp_metric)+ " : "+ getString(R.string.max_temp) +" "+  dailyWeather.getTempMax()+" " +getString(R.string.temp_metric));
            mWeatherDescript.setText(dailyWeather.getDescription());
            String wind = String.format("Wind speed " + "                    " + "%s" + "m/s",dailyWeather.getWind_speed());
            String humidity = String.format("Humidity " + "                      " + " %s" + "%%",dailyWeather.getHumidity());
            String pressure = String.format("Pressure " + "                      " + " %s" + "mm",dailyWeather.getPressure());
            String tempMin = String.format("Min " + "                           " + "   %s" + getString(R.string.temp_metric),dailyWeather.getTempMin());
            String tempMax = String.format("Max " + "                           " + "   %s" + getString(R.string.temp_metric),dailyWeather.getTempMax());
            String sunriseSunset = String.format("Sunrise/sunset " + "           " + "%s"+"/ %s" ,dailyWeather.getSunrise(),dailyWeather.getSunset());
            mWindSpeed.setText(wind);
            mHumidity.setText(humidity);
            mPressure.setText(pressure);
            mTempMax.setText(tempMax);
            mTempMin.setText(tempMin);
            mSunriseSunset.setText(sunriseSunset);

        }
    }



}
