package com.orlinskas.weatherwidget.widget;

import android.content.Context;

import com.orlinskas.weatherwidget.date.DateCalculator;
import com.orlinskas.weatherwidget.date.DateFormat;
import com.orlinskas.weatherwidget.date.DateHelper;

import java.util.Date;

public class WidgetUpdateChecker {
    private int widgetID;
    private Context context;
    private final int NEED_TIME_TO_UPDATE = 3;

    public WidgetUpdateChecker(int widgetID, Context context) {
        this.context = context;
        this.widgetID = widgetID;
    }

    public boolean check() {
        Widget widget = findWidgetInRepo(widgetID);

        String lastUpdate;

        if(widget != null) {
            lastUpdate = getLastUpdate(widget);
        }
        else {
            return false;
        }

        Date lastUpdateDate = DateHelper.parse(lastUpdate, DateFormat.YYYY_MM_DD_HH_MM);
        Date currentDate = DateHelper.getCurrentDate(DateFormat.YYYY_MM_DD_HH_MM);

        DateCalculator calculator = new DateCalculator();
        int hours = calculator.calculateDifferencesInHours(lastUpdateDate, currentDate);

        return hours > NEED_TIME_TO_UPDATE;
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

    private String getLastUpdate(Widget widget) {
        if(widget.getDaysForecast() != null && widget.getDaysForecast().size() > 0) {
            return  widget.getDaysForecast().get(0).getDayWeathers().get(0).getTimeOfDataForecast();
        }
        return "1996-01-22 15:00";
    }
}
