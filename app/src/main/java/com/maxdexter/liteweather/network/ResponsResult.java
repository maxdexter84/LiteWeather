package com.maxdexter.liteweather.network;

import androidx.lifecycle.MutableLiveData;

import com.maxdexter.liteweather.data.HistoryBox;
import com.maxdexter.liteweather.pojo.Result;
import com.maxdexter.liteweather.pojo.WeatherBox;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponsResult implements Callback<Result> {
    private static MutableLiveData<Boolean> mLiveData = new MutableLiveData<>();

    @Override
    public void onResponse(Call<Result> call, Response<Result> response) {
        if(response.code() == 200){
            Result result = response.body();
            WeatherBox.getInstance().setResult(result);
            mLiveData.setValue(true);
        }
    }

    @Override
    public void onFailure(Call<Result> call, Throwable t) {
        t.printStackTrace();
    }

    public static MutableLiveData<Boolean> getLiveData() {
        return mLiveData;
    }
}
