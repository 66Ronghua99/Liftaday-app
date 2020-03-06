package com.example.liftaday.utils;

import java.text.DateFormat;
import java.util.Date;

public class Common {
    public static String API_KEY = "401921bb0b3c0462924fb877e2e7e18b";
    public static String API_LINK = "https://api.openweathermap.org/data/2.5";
    public static String API_KEY_NEWS = "56ffa85b9f01492694f1aac5e0177c8d";
    public static String API_LINK_NEWS = "https://newsapi.org/v2/top-headlines?";


    public static String apiRequestNews() {
        StringBuilder sb = new StringBuilder(API_LINK_NEWS);
        sb.append(String.format("sources=%s&apiKey=%s","bbc-news", API_KEY_NEWS));
        return sb.toString();
    }

    public static String apiRequestWeather(String lat, String lon) {
        StringBuilder sb = new StringBuilder(API_LINK);
        sb.append(String.format("/weather?lat=%s&lon=%s&units=metric&appid=%s", lat, lon, API_KEY));
        return sb.toString();
    }

    public static String apiRequestForecast(String lat, String lon) {
        StringBuilder sb = new StringBuilder(API_LINK);
        sb.append(String.format("/forecast?lat=%s&lon=%s&units=metric&appid=%s", lat, lon, API_KEY));
        return sb.toString();
    }

    public static String getDate() {
        String date = DateFormat.getDateInstance(DateFormat.FULL).format(new Date());
        return date;
    }

    public static String getImage(String icon) {
        return String.format("https://openweathermap.org/img/w/%s.png", icon);
    }
}
