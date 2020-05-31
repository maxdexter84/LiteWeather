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
import com.maxdexter.liteweather.pojo.Daily;
import com.maxdexter.liteweather.pojo.HelperMethods;
import com.maxdexter.liteweather.pojo.WeatherBox;

/**
 * A simple {@link Fragment} subclass.
 */
public class TomorrowFragment extends Fragment {
    HelperMethods mHelperMethods = new HelperMethods();
    private int defaultPosition = 1;
    private TextView mCityName;
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
    private TextView mSunrise;
    private TextView mSunset;


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
        mSunrise = view.findViewById(R.id.sunrise_id);
        mSunset = view.findViewById(R.id.sunset_id);
    }

    @SuppressLint("SetTextI18n")
    private void setContent(int position){
        int feel = R.string.feeling_by;
        Daily weather = WeatherBox.getInstance().getResult().getDaily().get(1);
        HelperMethods mHelperMethods = new HelperMethods();
        String date = mHelperMethods.initDate(weather.getDt());
        String temp = (int)weather.getTemp().getDay() + " ℃";
        String desc = weather.getWeather().get(0).getDescription();
        int imgRes = mHelperMethods.getWeatherIcon(desc);
        mDateTime.setText(date);
        mCurrentTemp.setText(temp);
        mWeatherDescript.setText(desc);
        mImageWeather.setImageResource(imgRes);
        String wind = "Wind speed " + weather.getWindSpeed() + "m/s";
        String humidity = "Humidity " + weather.getHumidity()+ "%";
        String pressure = "Pressure " + weather.getPressure() + "mm";
        String tempMin = "Min " + weather.getTemp().getMin()+ "℃ ";
        String tempMax = "Max " +  weather.getTemp().getMax()+ "℃ ";
        String feeling = "Feeling by" +  weather.getFeelsLike().getDay()+ "℃ ";
        mWindSpeed.setText(wind);
        mHumidity.setText(humidity);
        mPressure.setText(pressure);
        mTempMax.setText(tempMax);
        mTempMin.setText(tempMin);
        mFeelingTemp.setText(feeling);


    }
    }


