package com.maxdexter.liteweather.fragments;

import android.content.Context;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    public static final String TAG = "BottomDialogFragment";
    private ItemClickListener mItemClickListener;
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
        view.findViewById(R.id.history_box).setOnClickListener(this);
        view.findViewById(R.id.info_box).setOnClickListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mItemClickListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mItemClickListener = null;
    }
    @Override public void onClick(View view) {
        TextView tvSelected = (TextView) view;
        mItemClickListener.onItemClick(tvSelected.getText().toString());
        dismiss();
    }
    public interface ItemClickListener {
        void onItemClick(String item);
    }
}
