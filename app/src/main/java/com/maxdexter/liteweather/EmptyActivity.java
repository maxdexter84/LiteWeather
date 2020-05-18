package com.maxdexter.liteweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;
import com.maxdexter.liteweather.adapter.HistoryAdapter;
import com.maxdexter.liteweather.data.HistoryBox;
import com.maxdexter.liteweather.data.HistoryWeather;
import com.maxdexter.liteweather.fragments.HistoryFragment;
import com.maxdexter.liteweather.fragments.InfoFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmptyActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
private static final String TYPE_FRAGMENT = "fragment_type";
public static final int HISTORY_FRAG = 0;
public static final int INFO_FRAG = 1;
int type;
Toolbar toolbar;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        type = getIntent().getExtras().getInt(TYPE_FRAGMENT);
        initToolbar();
        initDrawerLayout();
        initNavView();
        initFragment();
    }

    public static Intent newIntent(Context context,int typeFragment){
        Intent intent = new Intent(context, EmptyActivity.class);
        intent.putExtra(TYPE_FRAGMENT,typeFragment);
        return intent;
    }
    private void initNavView() {
        NavigationView navigationView = findViewById(R.id.nav_view_empty_activity);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void initDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer_empty_activity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open_draw,R.string.nav_close_draw);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
    private void initFragment(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.empty_activity_frag_container);
        if(fragment == null){
            if(type == HISTORY_FRAG){
                fragment = new HistoryFragment();
                fm.beginTransaction().add(R.id.empty_activity_frag_container,fragment).commit();
            }else if(type == INFO_FRAG){
                fragment = new InfoFragment();
                fm.beginTransaction().add(R.id.empty_activity_frag_container,fragment).commit();
            }
        }else{
            if(type == HISTORY_FRAG){
                fragment = new HistoryFragment();
                fm.beginTransaction().replace(R.id.empty_activity_frag_container,fragment).commit();
            }else if(type == INFO_FRAG){
                fragment = new InfoFragment();
                fm.beginTransaction().replace(R.id.empty_activity_frag_container,fragment).commit();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.history_box:
                Intent intent = EmptyActivity.newIntent(this,EmptyActivity.HISTORY_FRAG);
                startActivity(intent);
                return true;
            case R.id.info_box:
                Intent intent2 = EmptyActivity.newIntent(this,EmptyActivity.INFO_FRAG);
                startActivity(intent2);
                return true;
            case R.id.home:
                drawerLayout.closeDrawers();
                finish();
                return true;

        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        initSearchView(menu);
        return true;
    }

    private void initSearchView(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        EditText editText = searchView.findViewById(R.id.search_src_text);
        editText.setTextSize(24);
        editText.setHintTextColor(getResources().getColor(R.color.black));
        searchView.clearFocus();

    }
    private List<HistoryWeather> getHistory(){
       HashMap<String, HistoryWeather> list = HistoryBox.get(this).getHistoryWeatherList();
       List<HistoryWeather>weatherList = new ArrayList<>();
       for(Map.Entry<String,HistoryWeather>pair:list.entrySet()){
           HistoryWeather value = pair.getValue();
           weatherList.add(value);
       }
       return weatherList;
    }
}
