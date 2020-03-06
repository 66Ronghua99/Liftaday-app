package com.example.liftaday;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherAdapter extends BaseAdapter {

    private List<WeatherCondition> WL;
    private Context context;

    public WeatherAdapter(List<WeatherCondition> WC, Context context){
        this.WL = WC;
        this.context = context;
    }

    @Override
    public int getCount() {
        return WL.size();
    }

    @Override
    public WeatherCondition getItem(int position) {
        return WL.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View futureWeather = View.inflate(context,R.layout.activity_item_weather,null);
        ImageView img = futureWeather.findViewById(R.id.list_weatherImage);
        TextView text = futureWeather.findViewById(R.id.list_dateName);
        TextView temp = futureWeather.findViewById(R.id.list_temp);
        WeatherCondition wc = getItem(position);
        Picasso.get().load(wc.getIcon())
                .into(img);
        text.setText(wc.getDay());
        temp.setText(wc.getTemp());
        return futureWeather;
    }
}
