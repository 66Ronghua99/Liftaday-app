package com.example.liftaday.utils.forecast;

import java.util.List;

public class Forecast {
    private List<ForecastList> list;

    public Forecast() {}

    public Forecast(List<ForecastList> list) {
        this.list = list;
    }

    public List<ForecastList> getList() {
        return list;
    }

    public void setList(List<ForecastList> list) {
        this.list = list;
    }
}
