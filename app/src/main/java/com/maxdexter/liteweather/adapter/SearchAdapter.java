package com.maxdexter.liteweather.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.maxdexter.liteweather.R;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    ArrayList<String> mList;

    public void setListener(Listener listener) {
        mListener = listener;
    }

    private Listener mListener;
    interface Listener{
        void onClick(int position);
    }

    public SearchAdapter(ArrayList<String> list){
        this.mList = list;
    }
    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, final int position) {
       String mCityName = mList.get(position);
        holder.bind(mCityName);
        View view = holder.itemView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
            TextView cityName;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.city_name_search_card_item_id);
        }
        void bind(String city) {
            cityName.setText(city);
        }
    }
}
