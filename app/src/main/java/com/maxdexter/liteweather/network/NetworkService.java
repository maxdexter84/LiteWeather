package com.maxdexter.liteweather.network;
import com.maxdexter.liteweather.pojo.Result;
import com.maxdexter.liteweather.pojo.coord.CoordRes;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private boolean downloadOver = false;
    public static String MY_API_KEY = "073f40e104f2129961514beb51a721d2";
   private static NetworkService instance;
   private OpenWeatherTenDays service;
   private OpenWeatherService coordService;
    Retrofit retrofit;
   private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    public static NetworkService getInstance() {
        if(instance == null){
            instance = new NetworkService();
        }
        return instance;
    }

    public NetworkService() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
   public void loadData(String lat,String lon,String apiKey,String units){
        service = retrofit.create(OpenWeatherTenDays.class);
        Call<Result> call = service.getWeather(lat,lon,apiKey,units);
        call.enqueue(new ResponsResult());


    }
    public void loadCoord(String city,String api ,String units){
        coordService = retrofit.create(OpenWeatherService.class);
        Call<CoordRes> call = coordService.getCoord(city,api,units);
        call.enqueue(new ResponsCoord());

    }
}
