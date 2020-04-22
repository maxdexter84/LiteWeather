package com.maxdexter.liteweather.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.maxdexter.liteweather.R;
import com.maxdexter.liteweather.data.Weather;

import java.util.List;
import java.util.Map;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder>{
    private List< Weather> sWeatherList;
//Создаем конструтктор адаптера, в который передаем список sWeatherList
    public WeatherAdapter(List<Weather> weatherList){
        this.sWeatherList = weatherList;
    }
    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Подключаем представление карточки в список
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
        Weather weather = sWeatherList.get(position);
        holder.bind(weather);
    }

    @Override
    public int getItemCount() {
        return sWeatherList.size();
    }

 static class ViewHolder extends RecyclerView.ViewHolder{
     TextView itemDateTime;
     TextView itemCurrentTemp;
     TextView itemWeatherDescript;
     ImageView itemImageWeather;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
               itemDateTime = itemView.findViewById(R.id.item_date_time_current_text_view_id);
               itemCurrentTemp = itemView.findViewById(R.id.item_current_temp_text_view_id);
               itemWeatherDescript = itemView.findViewById(R.id.item_weather_description_text_view_id);
               itemImageWeather = itemView.findViewById(R.id.item_image_weather_image_id);
        }
        void bind(Weather weather){
            itemDateTime.setText(weather.getDate().toString());
            itemCurrentTemp.setText(weather.getCurrentTemp());
            itemWeatherDescript.setText(weather.getWeatherDescription());
            itemImageWeather.setImageResource(weather.getImageResourceId());
        }
    }
}
