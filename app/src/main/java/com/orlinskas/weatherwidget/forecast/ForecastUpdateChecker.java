package com.orlinskas.weatherwidget.forecast;

import com.orlinskas.weatherwidget.date.DateHelper;

public class ForecastUpdateChecker {
    public boolean check(ForecastFiveDay forecastFiveDay) {
        String firstForecastDayDate = forecastFiveDay.getDays()[0].getDayDate();
        if(firstForecastDayDate == null){
           return true;
        }
        else {
            return !firstForecastDayDate.equals(DateHelper.getCurrent());
        }
    }
}
