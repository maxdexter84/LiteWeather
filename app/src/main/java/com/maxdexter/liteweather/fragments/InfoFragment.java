package com.maxdexter.liteweather.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.maxdexter.liteweather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends BottomSheetDialogFragment {

    public static InfoFragment newInstance(){
        return new InfoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }
}
