package com.example.liftaday.utils.forecast;

import java.util.List;

public class ForecastList {
    private ForecastMain main;
    private List<ForecastWeather> weather;

    public ForecastList(ForecastMain main, List<ForecastWeather> weather) {
        this.main = main;
        this.weather = weather;
    }

    public ForecastMain getMain() {
        return main;
    }

    public void setMain(ForecastMain main) {
        this.main = main;
    }

    public List<ForecastWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<ForecastWeather> weather) {
        this.weather = weather;
    }
}
