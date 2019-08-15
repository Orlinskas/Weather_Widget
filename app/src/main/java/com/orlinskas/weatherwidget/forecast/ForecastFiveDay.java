package com.orlinskas.weatherwidget.forecast;

import java.io.Serializable;

public class ForecastFiveDay implements Serializable {
    private ForecastOneDay[] days;

    public ForecastFiveDay() {
        this.days = new ForecastOneDay[5];
    }

    public ForecastOneDay[] getDays() {
        return days;
    }

    public void setDays(ForecastOneDay[] days) {
        this.days = days;
    }
}
