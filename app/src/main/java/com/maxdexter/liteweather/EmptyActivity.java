package com.maxdexter.liteweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.maxdexter.liteweather.fragments.HistoryFragment;
import com.maxdexter.liteweather.fragments.InfoFragment;

public class EmptyActivity extends AppCompatActivity {
private static final String TYPE_FRAGMENT = "fragment_type";
public static final int HISTORY_FRAG = 0;
public static final int INFO_FRAG = 1;
int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        type = getIntent().getExtras().getInt(TYPE_FRAGMENT);
        initFragment();
    }

    public static Intent newIntent(Context context,int typeFragment){
        Intent intent = new Intent(context, EmptyActivity.class);
        intent.putExtra(TYPE_FRAGMENT,typeFragment);
        return intent;
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
}
