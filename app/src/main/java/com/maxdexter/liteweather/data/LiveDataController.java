package com.maxdexter.liteweather.data;



import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class LiveDataController {
    private static LiveDataController sLiveDataController;

    private MutableLiveData<String> liveData = new MutableLiveData<>();

   public LiveData<String> getData() {
        return liveData;
    }
    public void setData(String city){
        liveData.setValue(city);
    }

    private LiveDataController(Context context) {

    }


    public static LiveDataController get(Context context){
    if(sLiveDataController == null){
        sLiveDataController = new LiveDataController(context);
    }
        return sLiveDataController;
    }

    public LiveDataController() {
    }
}
