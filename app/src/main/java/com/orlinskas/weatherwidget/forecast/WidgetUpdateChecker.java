package com.orlinskas.weatherwidget.forecast;

import android.content.Context;

import com.orlinskas.weatherwidget.date.DateCalculator;
import com.orlinskas.weatherwidget.date.DateFormat;
import com.orlinskas.weatherwidget.date.DateHelper;
import com.orlinskas.weatherwidget.preferences.Preferences;
import com.orlinskas.weatherwidget.widget.Widget;

import java.util.Date;

public class WidgetUpdateChecker {
    private Widget widget;
    private Context context;

    public WidgetUpdateChecker(Widget widget, Context context) {
        this.widget = widget;
        this.context = context;
    }

    public boolean check() {
        Preferences preferences = Preferences.getInstance(context, Preferences.WIDGET_UPDATE_DATES);
        String lastUpdate = preferences.getData(String.valueOf(widget.getId()) ,"1996.01.22 15:00");

        Date lastUpdateDate = DateHelper.parse(lastUpdate, DateFormat.YYYY_MM_DD_HH_MM);
        Date currentDate = DateHelper.getCurrentDate(DateFormat.YYYY_MM_DD_HH_MM);

        DateCalculator calculator = new DateCalculator();
        int hours = calculator.calculateDifferencesInHours(lastUpdateDate, currentDate);

        return hours > 12;

        //if(widget.getDaysForecast() == null){
        //   return true;
        //}
        //else {
        //    String firstForecastDayDate = widget.getDaysForecast().get(0).getDayDate();
        //    return !firstForecastDayDate.equals(DateHelper.getCurrent(DateFormat.YYYY_MM_DD));
        //}
    }
}
