package com.orlinskas.weatherwidget.forecast;

import com.orlinskas.weatherwidget.date.DateFormat;
import com.orlinskas.weatherwidget.date.DateHelper;
import com.orlinskas.weatherwidget.widget.Widget;

import java.util.Date;

public class WidgetUpdateChecker {
    public boolean check(Widget widget) {
        if(widget.getForecastFiveDay() == null){
           return true;
        }
        else {
            Date firstForecastDayDate = widget.getForecastFiveDay().getDays()[0].getDayDate();
            return !firstForecastDayDate.equals(DateHelper.getCurrentDate(DateFormat.YYYY_MM_DD));
        }
    }
}
