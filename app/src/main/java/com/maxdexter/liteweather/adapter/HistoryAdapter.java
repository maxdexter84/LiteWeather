package com.maxdexter.liteweather.adapter;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maxdexter.liteweather.R;
import com.maxdexter.liteweather.data.HistoryWeather;
import com.maxdexter.liteweather.pojo.HelperMethods;

import java.util.List;

import static android.provider.Settings.System.getString;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {


     OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }
    //Интерфейс для обработки нажатий
    public interface OnItemClickListener{
        void onItemClick(View view,String city);
    }

    private List<HistoryWeather> sWeatherList;

    //Создаем конструтктор адаптера, в который передаем список sWeatherList
    public HistoryAdapter(List<HistoryWeather> weatherList){
        this.sWeatherList = weatherList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Подключаем представление карточки в список
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        HistoryWeather history = sWeatherList.get(position);
        holder.bind(history);
        View view = holder.itemView;
    }

    @Override
    public int getItemCount() {
        return sWeatherList.size();
    }



     class ViewHolder extends RecyclerView.ViewHolder {
       TextView cityName;
        TextView itemDateTime;
        TextView itemCurrentTemp;
        ImageView itemImageWeather;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.city_name_history);
            itemDateTime = itemView.findViewById(R.id.item_date_time_current_text_view_id);
            itemCurrentTemp = itemView.findViewById(R.id.item_current_temp_text_view_id);
            itemImageWeather = itemView.findViewById(R.id.item_image_weather_image_id);
        }
        void bind(final HistoryWeather weather){
            int imgRes = weather.getImageResourceId();
            String date = weather.getDT();
            cityName.setText(weather.getCityName());
            itemDateTime.setText(date);
            itemCurrentTemp.setText( weather.getTempDay() + " ℃");
            itemImageWeather.setImageResource(imgRes);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mItemClickListener != null){
                        mItemClickListener.onItemClick(v,weather.getCityName());
                    }
                }
            });

        }


    }

}
