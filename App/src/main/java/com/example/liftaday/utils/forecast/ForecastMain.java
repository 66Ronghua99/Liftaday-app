package com.example.liftaday.utils.forecast;

public class ForecastMain {
    private double temp;

    public ForecastMain(double temp) {
        this.temp = temp;
    }

    public long getTemp() {
        return Math.round(temp);
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
