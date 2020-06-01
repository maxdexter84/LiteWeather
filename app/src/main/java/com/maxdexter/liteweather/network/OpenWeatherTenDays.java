package com.maxdexter.liteweather.network;

import com.maxdexter.liteweather.pojo.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
//"https://api.openweathermap.org/data/2.5/onecall?q=moscow&&apikey=073f40e104f2129961514beb51a721d2&units=metric
public interface OpenWeatherTenDays {
    @GET("onecall")
    Call<Result> getWeather(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid, @Query("units")String units);
}
