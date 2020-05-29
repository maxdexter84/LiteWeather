package com.maxdexter.liteweather.pojo;

import android.content.Context;

import java.util.ArrayList;

public class WeatherBox {
    public Result getResult() {
        return mResult;
    }

    public void setResult(Result result) {
        mResult = result;
    }

    Result mResult;
    public static WeatherBox getInstance(Context context) {
        if(instance == null){
            instance = new WeatherBox(context);
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

    public WeatherBox(Context context) {
        mList = new ArrayList<>();

    }
}
