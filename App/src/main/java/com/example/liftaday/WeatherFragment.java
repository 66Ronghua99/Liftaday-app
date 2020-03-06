package com.example.liftaday;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment{

    private WeatherAdapter adapter;
    private List<WeatherCondition> weatherList;
    private ListView lvWeather;
    private View view;

    private TextView txtCity;
    private TextView txtDate;
    private TextView txtTemp;
    private TextView txtWeather;
    private TextView txtWind;
    private TextView txtHumidity;
    private TextView txtTempToday;
    private ImageView imageWeather;

//    OpenWeatherMap openWeatherMap = new OpenWeatherMap();
//    double lat, lon;
//
//    LocationManager locationManager;
//    String provider;
//    int MY_PERMISSION = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        init();

        txtCity = view.findViewById(R.id.location);
        txtDate = view.findViewById(R.id.weekDay);
        txtTemp = view.findViewById(R.id.temp);
        txtWeather = view.findViewById(R.id.wc);
        txtWind = view.findViewById(R.id.wind);
        txtHumidity = view.findViewById(R.id.humidity);
        txtTempToday = view.findViewById(R.id.temp_day);
        imageWeather = view.findViewById(R.id.weather_condition);

        txtCity.setText(SplashScreen.city);
        txtDate.setText(SplashScreen.date);
        txtWeather.setText(SplashScreen.weather);
        txtHumidity.setText(SplashScreen.humidity);
        txtTemp.setText(SplashScreen.temp);
        txtWind.setText(SplashScreen.wind);
        txtTempToday.setText(SplashScreen.tempToday);
        Picasso.get().load(SplashScreen.weatherImage)
                    .into(imageWeather);

        return view;
    }

    private void init() {
        lvWeather = view.findViewById(R.id.List_Weather);
        weatherList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            WeatherCondition wc = new WeatherCondition(SplashScreen.dayForecast[i],
                    SplashScreen.imageForecast[i], SplashScreen.tempForecast[i]);
            weatherList.add(wc);
        }
        adapter = new WeatherAdapter(weatherList, getContext());
        lvWeather.setAdapter(adapter);
    }
}
