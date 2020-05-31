package com.maxdexter.liteweather.network;

import androidx.lifecycle.MutableLiveData;

import com.maxdexter.liteweather.pojo.Result;
import com.maxdexter.liteweather.pojo.WeatherBox;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponsResult implements Callback<Result> {
    private static MutableLiveData<Boolean> mLiveData = new MutableLiveData<>();
    private boolean downloadOver = false;
    @Override
    public void onResponse(Call<Result> call, Response<Result> response) {
        if(response.code()== 200){
            Result result = response.body();
            WeatherBox.getInstance().setResult(result);
            downloadOver = true;
            mLiveData.setValue(downloadOver);
        }
    }

    @Override
    public void onFailure(Call<Result> call, Throwable t) {

    }

    public static MutableLiveData<Boolean> getLiveData() {
        return mLiveData;
    }
}
