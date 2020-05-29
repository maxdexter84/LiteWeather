package com.maxdexter.liteweather.network;

import com.maxdexter.liteweather.pojo.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//https://api.openweathermap.org/data/2.5/weather?q=moscow&&apikey=073f40e104f2129961514beb51a721d2&units=metric
//api.openweathermap.org/data/2.5/forecast?q=moscow&appid=073f40e104f2129961514beb51a721d2&units=metric
public interface OpenWeatherService {
    @GET("forecast")
    Call<Result> getCoord(@Query("q") String city, @Query("appid") String apiKey, @Query("units") String metric);
}
