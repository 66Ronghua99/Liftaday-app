package com.example.liftaday;

import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.widget.TextView;

public class WeatherCondition {
    private String day;
    private String icon;
    private String temp;

    public WeatherCondition(String day, String icon, String temp) {
        this.day = day;
        this.icon = icon;
        this.temp = temp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
