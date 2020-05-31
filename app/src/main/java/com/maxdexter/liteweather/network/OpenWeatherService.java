package com.maxdexter.liteweather.network;

import com.maxdexter.liteweather.pojo.Result;
import com.maxdexter.liteweather.pojo.coord.CoordRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//https://api.openweathermap.org/data/2.5/weather?q=moscow&&apikey=073f40e104f2129961514beb51a721d2&units=metric

public interface OpenWeatherService {
    @GET("weather")
    Call<CoordRes> getCoord(@Query("q") String city, @Query("apikey") String apiKey, @Query("units") String metric);
}
