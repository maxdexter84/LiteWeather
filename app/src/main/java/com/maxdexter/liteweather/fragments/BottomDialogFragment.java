package com.maxdexter.liteweather.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.OnLifecycleEvent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.maxdexter.liteweather.R;
import com.maxdexter.liteweather.SettingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = "BottomDialogFragment";
  public static BottomDialogFragment newInstance(){
       return new BottomDialogFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_dialog, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.history_box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryFragment historyFragment = HistoryFragment.newInstance();
                historyFragment.show(getFragmentManager(),"history fragment");
                dismiss();
            }
        });
        view.findViewById(R.id.info_box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoFragment infoFragment = InfoFragment.newInstance();
                infoFragment.show(getFragmentManager(),"blank fragment");
                dismiss();
            }
        });
        view.findViewById(R.id.tools_box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                dismiss();
            }
        });

    }
}
