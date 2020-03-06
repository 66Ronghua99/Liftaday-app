package com.example.liftaday;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class OutfitFragment extends Fragment {

    private String tc = SplashScreen.t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int t = Integer.parseInt(tc);

        if (t > 20)
            return inflater.inflate(R.layout.fragment_outfit_20, container, false);
        else if (t >= 10 && t <= 20)
            return inflater.inflate(R.layout.fragment_outfit_10_20, container, false);
        else
            return inflater.inflate(R.layout.fragment_outfit_10, container, false);

    }
}
