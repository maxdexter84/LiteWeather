package com.maxdexter.liteweather.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maxdexter.liteweather.data.DailyWeather;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<DailyWeather> weatherList;

    public HistoryAdapter(List<DailyWeather> weatherList) {
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
  private class HistoryHolder extends RecyclerView.ViewHolder{

      public HistoryHolder(@NonNull View itemView) {
          super(itemView);
      }
  }
}
