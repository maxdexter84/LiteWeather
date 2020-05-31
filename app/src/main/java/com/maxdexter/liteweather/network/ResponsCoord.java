package com.maxdexter.liteweather.network;
import com.maxdexter.liteweather.data.HistoryWeather;
import com.maxdexter.liteweather.pojo.HelperMethods;
import com.maxdexter.liteweather.pojo.WeatherBox;
import com.maxdexter.liteweather.pojo.coord.CoordRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponsCoord implements Callback<CoordRes> {


    @Override
    public void onResponse(Call<CoordRes> call, Response<CoordRes> response) {
            WeatherBox.getInstance().setCoordRes(response.body());

    }

    @Override
    public void onFailure(Call<CoordRes> call, Throwable t) {
        t.printStackTrace();
    }
}
