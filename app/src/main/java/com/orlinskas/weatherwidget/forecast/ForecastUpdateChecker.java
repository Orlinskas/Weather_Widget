package com.orlinskas.weatherwidget.forecast;

import com.orlinskas.weatherwidget.date.DateFormat;
import com.orlinskas.weatherwidget.date.DateHelper;

import java.util.Date;

public class ForecastUpdateChecker {
    public boolean check(ForecastFiveDay forecastFiveDay) {
        if(forecastFiveDay == null){
           return true;
        }
        else {
            Date firstForecastDayDate = forecastFiveDay.getDays()[0].getDayDate();
            return !firstForecastDayDate.equals(DateHelper.getCurrentDate(DateFormat.YYYY_MM_DD));
        }
    }
}
