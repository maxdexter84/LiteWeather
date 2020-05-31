package com.maxdexter.liteweather.pojo;

import android.content.Context;

import com.maxdexter.liteweather.pojo.coord.CoordRes;

import java.util.ArrayList;

public class WeatherBox {
    public Result getResult() {
        return mResult;
    }

    public void setResult(Result result) {
        mResult = result;
    }

    private Result mResult;
    private CoordRes mCoordRes;

    public CoordRes getCoordRes() {
        return mCoordRes;
    }

    public void setCoordRes(CoordRes coordRes) {
        mCoordRes = coordRes;
    }

    public static WeatherBox getInstance() {
        if(instance == null){
            instance = new WeatherBox();
        }
        return instance;
    }

    private static WeatherBox instance;

    public ArrayList<Result> getList() {
        return mList;
    }

    public void setList(ArrayList<Result> list) {
        mList = list;
    }

    private ArrayList<Result>mList;

    public WeatherBox() {
        mList = new ArrayList<>();

    }
}
