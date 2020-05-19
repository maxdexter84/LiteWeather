package com.maxdexter.liteweather.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.maxdexter.liteweather.R;
import com.maxdexter.liteweather.adapter.HistoryAdapter;
import com.maxdexter.liteweather.data.HistoryBox;
import com.maxdexter.liteweather.data.HistoryWeather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BottomSheetDialogFragment {
    private List<HistoryWeather> mWeatherList;

public static BlankFragment newInstance(){
    return new BlankFragment();
}

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        initList();

        RecyclerView recyclerView = view.findViewById(R.id.history_recycler);
        HistoryAdapter historyAdapter = new HistoryAdapter(mWeatherList);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
    private void initList() {
        HashMap<String, HistoryWeather> map = HistoryBox.get(getActivity()).getHistoryWeatherList();
        mWeatherList = new ArrayList<>();
        for(Map.Entry<String,HistoryWeather>pair:map.entrySet()){
            String key = pair.getKey();
            HistoryWeather value = pair.getValue();
            mWeatherList.add(value);
        }
    }

}
