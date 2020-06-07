package com.maxdexter.liteweather.pojo;
import androidx.lifecycle.LiveData;

import com.maxdexter.liteweather.data.LiveDataController;
import com.maxdexter.liteweather.pojo.coord.CoordRes;

import java.util.ArrayList;

public class WeatherBox {

    public Result getResult() {
        return mResult;
    }
    public interface listener{
        void getCoordLatLon(double lat, double lon);
    }
   public listener mListener;

    public void setListener(listener listener) {
        mListener = listener;
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
        if(coordRes != null){
            double lat = coordRes.getCoord().getLat();
            double lon = coordRes.getCoord().getLon();
            if(mListener != null){
                mListener.getCoordLatLon(lat,lon);
            }
        }
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
