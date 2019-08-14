package com.orlinskas.weatherwidget.forecast;

public class ForecastFiveDay {
    private ForecastOneDay[] days = new ForecastOneDay[5];

    public ForecastOneDay[] getDays() {
        return days;
    }

    public void setDays(ForecastOneDay[] days) {
        this.days = days;
    }
}
