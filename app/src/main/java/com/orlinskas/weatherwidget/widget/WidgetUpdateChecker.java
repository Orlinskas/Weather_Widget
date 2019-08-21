package com.orlinskas.weatherwidget.widget;

import android.content.Context;

import com.orlinskas.weatherwidget.date.DateCalculator;
import com.orlinskas.weatherwidget.date.DateFormat;
import com.orlinskas.weatherwidget.date.DateHelper;
import com.orlinskas.weatherwidget.preferences.Preferences;

import java.util.Date;

public class WidgetUpdateChecker {
    private int widgetID;
    private Context context;
    private Preferences preferences;

    public WidgetUpdateChecker(int widgetID, Context context) {
        this.context = context;
        this.widgetID = widgetID;
    }

    public boolean check() {
        Widget widget = findWidgetInRepo(widgetID);

        try {
            preferences = Preferences.getInstance(context, Preferences.WIDGET_UPDATE_DATES);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String lastUpdate;

        if(widget != null & preferences != null) {
            lastUpdate = preferences.getData(String.valueOf(widget.getId()) ,"1996.01.22 15:00");
        }
        else {
            return false;
        }

        Date lastUpdateDate = DateHelper.parse(lastUpdate, DateFormat.YYYY_MM_DD_HH_MM);
        Date currentDate = DateHelper.getCurrentDate(DateFormat.YYYY_MM_DD_HH_MM);

        DateCalculator calculator = new DateCalculator();
        int hours = calculator.calculateDifferencesInHours(lastUpdateDate, currentDate);

        return hours > 12;
    }

    private Widget findWidgetInRepo(int widgetID) {
        Widget widget = null;
        WidgetRepository repository = new WidgetRepository(context);
        try {
            widget = repository.find(widgetID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return widget;
    }
}
