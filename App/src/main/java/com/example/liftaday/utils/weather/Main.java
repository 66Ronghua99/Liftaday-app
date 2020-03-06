package com.example.liftaday.utils.weather;

public class Main {
    private double temp;
    private double temp_min;
    private double temp_max;
    private double humidity;
    private double feels_like;
    private double pressure;

    public Main(double temp, double temp_min, double temp_max, double humidity, double feels_like, double pressure) {
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.humidity = humidity;
        this.feels_like = feels_like;
        this.pressure = pressure;
    }

    public long getTemp() {
        return Math.round(temp);
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public long getTemp_min() {
        return Math.round(temp_min);
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public long getTemp_max() {
        return Math.round(temp_max);
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public long getHumidity() {
        return Math.round(humidity);
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = feels_like;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
}
