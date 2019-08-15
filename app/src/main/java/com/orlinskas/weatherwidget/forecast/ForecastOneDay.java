package com.orlinskas.weatherwidget.forecast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ForecastOneDay implements Serializable {
    private Date dayDate;
    private ArrayList<Weather> dayWeathers;

    public ForecastOneDay(Date dayDate) {
        this.dayDate = dayDate;
        this.dayWeathers = new ArrayList<>();
    }

    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    public ArrayList<Weather> getDayWeathers() {
        return dayWeathers;
    }

    public void setDayWeathers(ArrayList<Weather> dayWeathers) {
        this.dayWeathers = dayWeathers;
    }
}
