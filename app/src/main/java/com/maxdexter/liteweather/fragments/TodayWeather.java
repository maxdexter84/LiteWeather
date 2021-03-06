package com.maxdexter.liteweather.fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.maxdexter.liteweather.R;
import com.maxdexter.liteweather.network.ResponsResult;
import com.maxdexter.liteweather.pojo.Daily;
import com.maxdexter.liteweather.pojo.WeatherBox;
import com.maxdexter.liteweather.pojo.HelperMethods;
import com.maxdexter.liteweather.pojo.coord.CoordRes;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWeather extends Fragment{
    private int defaultPosition = 0;
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
    private HelperMethods mHelperMethods;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      final View view = inflater.inflate(R.layout.fragment_today_weather, container, false);
        init(view);
        //setContent(defaultPosition);
        mHelperMethods = new HelperMethods();

        ResponsResult.getLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                boolean load = aBoolean;
                if(aBoolean){
                    initView();
                }
            }
        });
        return view;
    }

    private void initView() {
        CoordRes coordRes = WeatherBox.getInstance().getCoordRes();
        Daily daily = WeatherBox.getInstance().getResult().getDaily().get(0);
        String desc = daily.getWeather().get(0).getDescription();
        String wind = String.format("%s" + "m/s",daily.getWindSpeed());
        String humidity = String.format("%s" + "%%",daily.getHumidity());
        String pressure = String.format("%s" + "mm",daily.getPressure());
        String tempMorn = String.format("%s" + getString(R.string.temp_metric),daily.getTemp().getMorn());
        String tempEve = String.format("%s" + getString(R.string.temp_metric),daily.getTemp().getEve());
        String sunset = mHelperMethods.initTime(daily.getSunset());
        String sunrise = mHelperMethods.initTime(daily.getSunrise());
        String date = mHelperMethods.initDate(daily.getDt());
        String currentTemp = (int)coordRes.getMain().getTemp()+"";
        int resid = mHelperMethods.getWeatherIcon(daily.getWeather().get(0).getDescription());
        mCurrentTemp.setText(currentTemp + " " + getString(R.string.temp_metric));
        mFeelingTemp.setText(getString(R.string.feeling) +" " + daily.getFeelsLike().getDay() + " " + getString(R.string.temp_metric));
        mDayNightTemp.setText(getString(R.string.min_temp) +" "+ daily.getTemp().getMin()+" " +getString(R.string.temp_metric)+ " : "+ getString(R.string.max_temp) +" "+  daily.getTemp().getMax()+" " +getString(R.string.temp_metric));
        mWeatherDescript.setText(desc);
        mImageWeather.setImageResource(resid);
        mDateTime.setText(date);
        mWindSpeed.setText(wind);
        mHumidity.setText(humidity);
        mPressure.setText(pressure);
        mTempMax.setText(tempEve);
        mTempMin.setText(tempMorn);
        mSunrise.setText(sunrise);
        mSunset.setText(sunset);
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
}
