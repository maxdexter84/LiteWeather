package com.maxdexter.liteweather.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
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

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends BottomSheetDialogFragment {
    ConstraintLayout mConstraintLayout;
    private List<HistoryWeather> mWeatherList;
    private static final int ID_DELETE = 10;
    private HistoryAdapter historyAdapter;
    private RecyclerView recyclerView;
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
        mConstraintLayout = view.findViewById(R.id.history_frag);

        return view;
    }

    private void initRecyclerAdapter(View view) {
        recyclerView = view.findViewById(R.id.history_recycler);
        historyAdapter = new HistoryAdapter(mWeatherList);
        historyAdapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v,String city) {
                WeatherLab.get(getContext()).setData(city);
                dismiss();
            }
        });
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSimpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initList() {
       mWeatherList = HistoryBox.get(getActivity()).getHistoryWeatherList();
    }
    //создаем ItemTouchHelper.SimpleCallback для того что бы иметь возможность свайпать элементы списка или передвигать их и менять местами
    //В качестве аргументов передаем 0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT
    //0 значит что перетаскивать мы не будем, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT значит что мы сможем свайпать как с лева на право так и с права на лево
    private ItemTouchHelper.SimpleCallback mSimpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {//ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAbsoluteAdapterPosition();// выясняем позицию элемента в адаптере
            final HistoryWeather historyWeather = mWeatherList.get(position);
            switch (direction){// через свич выясняем напраление свайпа и в зависимости от этого назначаем действие
                case ItemTouchHelper.LEFT:
                    HistoryBox.get(getContext()).deleteHistoryWeather(historyWeather);//Удаляем выбранный объект из базы данных
                    mWeatherList.remove(position);// удаляем из списка
                    historyAdapter.notifyItemRemoved(position);//обновляем адаптер
                    break;

            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(),R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.delete_icon)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

}
