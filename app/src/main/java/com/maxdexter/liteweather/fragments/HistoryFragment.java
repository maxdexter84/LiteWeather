package com.maxdexter.liteweather.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.maxdexter.liteweather.MainActivity;
import com.maxdexter.liteweather.R;
import com.maxdexter.liteweather.adapter.HistoryAdapter;
import com.maxdexter.liteweather.data.AppCache;
import com.maxdexter.liteweather.data.HistoryBox;
import com.maxdexter.liteweather.data.HistoryWeather;
import com.maxdexter.liteweather.data.WeatherLab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends BottomSheetDialogFragment {
    private List<HistoryWeather> mWeatherList;
    private static final int ID_DELETE = 10;


    public static HistoryFragment newInstance(){
       return new HistoryFragment();
   }



    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        initList();
        initRecyclerAdapter(view);


        return view;
    }

    private void initRecyclerAdapter(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.history_recycler);
        HistoryAdapter historyAdapter = new HistoryAdapter(mWeatherList);
        historyAdapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v,String city) {
                WeatherLab.get(getContext()).setData(city);
                dismiss();
            }
        });
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initList() {
       mWeatherList = HistoryBox.get(getActivity()).getHistoryWeatherList();
    }


}
