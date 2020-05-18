package com.maxdexter.liteweather.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.maxdexter.liteweather.MainActivity;
import com.maxdexter.liteweather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PagerFragment extends Fragment{
    public PagerFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager,container,false);
        initViewPager(view);
        return view;
    }



    private void initViewPager(View view ) {
       PagerFragment.ViewPagerFragment pagerAdapter = new PagerFragment.ViewPagerFragment(getFragmentManager());
        ViewPager pager = view.findViewById(R.id.view_pager_fragment_id);
        pager.setAdapter(pagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tabs_fragment);
        tabLayout.setupWithViewPager(pager);
    }

    public class ViewPagerFragment extends FragmentPagerAdapter {


        public ViewPagerFragment(@NonNull FragmentManager fm) {
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
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return getResources().getText(R.string.today_button) ;
                case 1:return getResources().getText(R.string.tomorrow_button);
                case 2:return getResources().getText(R.string.week_button);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}

