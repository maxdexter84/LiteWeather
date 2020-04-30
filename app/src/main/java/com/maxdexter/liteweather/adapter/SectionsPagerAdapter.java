package com.maxdexter.liteweather.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.maxdexter.liteweather.fragments.TenDaysWeather;
import com.maxdexter.liteweather.fragments.TodayWeather;
import com.maxdexter.liteweather.fragments.TomorrowFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new TodayWeather();
            case 1: return new TomorrowFragment();
            case 2: return new TenDaysWeather();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
