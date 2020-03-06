package com.example.liftaday;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class NewsAdapter extends PagerAdapter {

    List<View> newsList;

    public NewsAdapter(List<View> newsList) {
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(newsList.get(position));

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        container.addView(newsList.get(position));
//        Log.i("tag","add a view");
        return newsList.get(position);
    }

    @Override

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
