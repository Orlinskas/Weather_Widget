package com.orlinskas.weatherwidget.forecast;

public class ForecastFiveDay {
    private ForecastOneDay[] days;

    public ForecastFiveDay() {
        this.days = new ForecastOneDay[4];
    }

    public ForecastOneDay[] getDays() {
        return days;
    }

    public void setDays(ForecastOneDay[] days) {
        this.days = days;
    }
}
