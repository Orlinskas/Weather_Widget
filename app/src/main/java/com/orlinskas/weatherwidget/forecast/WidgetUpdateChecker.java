package com.orlinskas.weatherwidget.forecast;

import com.orlinskas.weatherwidget.date.DateFormat;
import com.orlinskas.weatherwidget.date.DateHelper;
import com.orlinskas.weatherwidget.widget.Widget;

public class WidgetUpdateChecker {
    public boolean check(Widget widget) {
        if(widget.getForecastFiveDay() == null){
           return true;
        }
        else {
            String firstForecastDayDate = widget.getForecastFiveDay().getDays()[0].getDayDate();
            return !firstForecastDayDate.equals(DateHelper.getCurrent(DateFormat.YYYY_MM_DD));
        }
    }
}
