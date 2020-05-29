package com.maxdexter.liteweather.network;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.maxdexter.liteweather.pojo.Result;
import com.maxdexter.liteweather.pojo.WeatherBox;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService  implements Callback<Result>{
   MutableLiveData<Boolean> mLiveData = new MutableLiveData<>();
    Context mContext;
    public MutableLiveData<Boolean> getLiveData() {
        return mLiveData;
    }
    boolean downloadOver = false;
    public  String MY_API_KEY = "073f40e104f2129961514beb51a721d2";
   private static NetworkService instance;
   OpenWeatherService service;
   private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    public static NetworkService getInstance(Context context) {
        if(instance == null){
            instance = new NetworkService(context);
        }
        return instance;
    }

    public NetworkService(Context context) {
        mContext = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(OpenWeatherService.class);
    }
   public void loadData(String city,String apiKey,String units){
        Call<Result> call = service.getCoord(city,apiKey,units);
        call.enqueue(this);

    }


    @Override
    public void onResponse(Call<Result> call, Response<Result> response) {
        if(response.code()== 200){
            Result result = response.body();
            WeatherBox.getInstance(mContext).setResult(result);
            downloadOver = true;
            mLiveData.setValue(downloadOver);
        }
    }

    @Override
    public void onFailure(Call<Result> call, Throwable t) {

    }
}
