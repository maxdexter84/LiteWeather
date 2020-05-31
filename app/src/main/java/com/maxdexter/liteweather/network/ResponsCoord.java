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
            int lat = (int)response.body().getCoord().getLat();
            int lon = (int)response.body().getCoord().getLon();
        NetworkService.getInstance().loadData(lat+"",lon+"","073f40e104f2129961514beb51a721d2","metric");
    }

    @Override
    public void onFailure(Call<CoordRes> call, Throwable t) {
        t.printStackTrace();
    }
}
