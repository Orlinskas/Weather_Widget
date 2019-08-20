package com.orlinskas.weatherwidget.ui.main;

import android.content.Context;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.orlinskas.weatherwidget.forecast.Forecast;
import com.orlinskas.weatherwidget.widget.Widget;
import com.orlinskas.weatherwidget.widget.WidgetModel;

public class WidgetPresenter implements WidgetContract.Presenter {
    private WidgetContract.View view;
    private WidgetContract.WidgetModel model;
    private Widget widget;
    private int dayNumber;
    private int dayCount;

    WidgetPresenter(Widget widget, Context context) {
        this.widget = widget;
        model = new WidgetModel(context);
        dayNumber = 0;
        dayCount = widget.getDaysForecast().size() - 1;
    }

    @Override
    public void attachView(WidgetContract.View view) {
        this.view = view;
    }

    @Override
    public void viewIsReady() {
        view.updateUI();
    }

    @Override
    public Widget getWidget() {
        return widget;
    }

    @Override
    public LinearLayout getIconsLayout() {
        return model.buildIconsLayout(getCurrentForecast());
    }

    @Override
    public LineChart getChartLayout() {
        return model.buildChartLayout(getCurrentForecast());
    }

    @Override
    public String getCurrentDate() {
        return getCurrentForecast().getDayDate();
    }

    private Forecast getCurrentForecast() {
        return widget.getDaysForecast().get(dayNumber);
    }

    @Override
    public String getChartDescription() {
        String countryCode = widget.getCity().getCountryCode();
        String cityName = widget.getCity().getName();
        return countryCode + "  " + cityName;
    }

    @Override
    public boolean prevDay() {
        if(dayNumber > 0){
            dayNumber--;
            view.updateUI();
            return true;
        }
        view.doToast(buildMessageAvailableDates());
        return false;
    }

    @Override
    public boolean nextDay() {
        if(dayNumber < dayCount){
            dayNumber++;
            view.updateUI();
            return true;
        }
        view.doToast(buildMessageAvailableDates());
        return false;
    }

    private String buildMessageAvailableDates() {
        try {
            return String.format(
                    "%1$s %2$s %3$s %4$s.",
                    "Доступно с",
                    widget.getDaysForecast().get(0).getDayDate(),
                    "до",
                    widget.getDaysForecast().get(dayCount).getDayDate()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return "Пока нет данных";
        }
    }

    @Override
    public void destroy() {
        model = null;
        view = null;
        widget = null;
    }
}
