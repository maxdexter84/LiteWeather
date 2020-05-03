package com.maxdexter.liteweather.adapter;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.maxdexter.liteweather.R;
import com.maxdexter.liteweather.data.DailyWeather;

import java.util.ArrayList;

import static android.provider.Settings.System.getString;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {




    private ArrayList<DailyWeather>sWeatherList;

//Создаем конструтктор адаптера, в который передаем список sWeatherList
    public DailyWeatherAdapter(ArrayList<DailyWeather>weatherList){
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
        DailyWeather weather = sWeatherList.get(position);
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
        void bind(DailyWeather weather){
            itemDateTime.setText(weather.getDT());
            itemCurrentTemp.setText(weather.getTempDay() + " ℃");
            itemWeatherDescript.setText(weather.getDescription());
            itemImageWeather.setImageResource(weather.getImageResourceId());
            String wind = "Wind speed " + weather.getWind_speed() + "m/s";
            String humidity = "Humidity " + weather.getHumidity()+ "%";
            String pressure = "Pressure " + weather.getPressure() + "mm";
            String tempMin = "Min " + weather.getTempMin()+ "℃ ";
            String tempMax = "Max " +  weather.getTempMax()+ "℃ ";
            String feeling = "Feeling by" +  weather.getFeeling()+ "℃ ";
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
