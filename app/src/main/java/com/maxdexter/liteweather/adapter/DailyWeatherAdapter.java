package com.maxdexter.liteweather.adapter;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.maxdexter.liteweather.R;
import com.maxdexter.liteweather.pojo.Daily;
import com.maxdexter.liteweather.pojo.HelperMethods;

import java.util.List;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {

   // private ArrayList<DailyWeather>sWeatherList;
    private List<Daily> sWeatherList;

//Создаем конструтктор адаптера, в который передаем список sWeatherList
    public DailyWeatherAdapter(List<Daily> weatherList){
        this.sWeatherList = weatherList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Подключаем представление карточки в список
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Daily weather = sWeatherList.get(position);
        holder.bind(weather);
        View view = holder.itemView;


    }

    @Override
    public int getItemCount() {
        return sWeatherList.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            LinearLayout details;
            TextView itemDateTime;
            TextView itemCurrentTemp;
            TextView itemWeatherDescript;
            ImageView itemImageWeather;
            TextView mWindSpeed;
            TextView mHumidity;
            TextView mPressure;
            TextView mTempMin;
            TextView mTempMax;
            TextView mFeeling;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            details = itemView.findViewById(R.id.weather_details_id);
            itemDateTime = itemView.findViewById(R.id.item_date_time_current_text_view_id);
            itemCurrentTemp = itemView.findViewById(R.id.item_current_temp_text_view_id);
            itemWeatherDescript = itemView.findViewById(R.id.item_weather_description_text_view_id);
            itemImageWeather = itemView.findViewById(R.id.item_image_weather_image_id);
            mWindSpeed = itemView.findViewById(R.id.wind_title_id);
            mHumidity = itemView.findViewById(R.id.humidity_title_id);
            mPressure = itemView.findViewById(R.id.pressure_title_id);
            mTempMax = itemView.findViewById(R.id.title_max_id);
            mTempMin = itemView.findViewById(R.id.title_min_id);
            mFeeling = itemView.findViewById(R.id.feeling_title_id);
        }
        void bind(Daily weather){
            HelperMethods mHelperMethods = new HelperMethods();
            String date = mHelperMethods.initDate(weather.getDt());
            String temp = (int)weather.getTemp().getDay() + " ℃";
            String desc = weather.getWeather().get(0).getDescription();
            int imgRes = mHelperMethods.getWeatherIcon(weather.getWeather().get(0).getDescription());
            itemDateTime.setText(date);
            itemCurrentTemp.setText(temp);
            itemWeatherDescript.setText(desc);
            itemImageWeather.setImageResource(imgRes);
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
            mFeeling.setText(feeling);

        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public void onClick(View v) {
            if(details.getVisibility() == View.GONE){
               details.setVisibility(View.VISIBLE);
            }else{
                details.setVisibility(View.GONE);
            }
        }
    }

}
