package com.maxdexter.liteweather.data;

import java.util.ArrayList;


public class SearchLab {
    public SearchLab() {
        prevSearched = new ArrayList<>();
        prevSearched.add("Москва");
        prevSearched.add("Санкт Петербург");
        prevSearched.add("Пермь");
    }

    private ArrayList<String> prevSearched = new ArrayList<>();


    public ArrayList<String> getPrevSearched() {
        return prevSearched;
    }

    public void addPrevSearched(String cityName) {
        if(!prevSearched.isEmpty()){
            if(!prevSearched.contains(cityName)){
                prevSearched.add(cityName);
            }
        }else{
            prevSearched.add(cityName);
        }


    }

}
