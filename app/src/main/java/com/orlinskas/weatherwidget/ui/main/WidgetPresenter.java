package com.orlinskas.weatherwidget.ui.main;

import android.content.Context;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.orlinskas.weatherwidget.chart.ChartBuilder;
import com.orlinskas.weatherwidget.chart.WeatherIconsLayoutBuilder;
import com.orlinskas.weatherwidget.forecast.Forecast;
import com.orlinskas.weatherwidget.widget.Widget;

public class WidgetPresenter implements WidgetContract.Presenter {
    private WidgetContract.View widgetView;
    private Widget widget;
    private Context context;
    private int dayNumber;
    private int dayCount;

    public WidgetPresenter(Widget widget, Context context) {
        this.widget = widget;
        this.context = context;
        dayNumber = 0;
        dayCount = widget.getDaysForecast().size();
    }

    @Override
    public void attachView(WidgetContract.View view) {
        this.widgetView = view;
    }

    @Override
    public void viewIsReady() {
        widgetView.updateUI();
    }

    @Override
    public Widget getWidget() {
        return widget;
    }

    @Override
    public LinearLayout getIconsLayout() {
        return buildLinearLayout(getForecastFromDay(dayNumber));
    }

    @Override
    public LineChart getChartLayout() {
        return buildChart(getForecastFromDay(dayNumber));
    }

    @Override
    public String getCurrentDate() {
        return getForecastFromDay(dayNumber).getDayDate();
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
            return true;
        }
        return false;
    }

    @Override
    public boolean nextDay() {
        if(dayNumber < dayCount){
            dayNumber++;
            return true;
        }
        return false;
    }

    private Forecast getForecastFromDay(int dayNumber) {
        return widget.getDaysForecast().get(dayNumber);
    }

    private LinearLayout buildLinearLayout(Forecast forecast) {
        WeatherIconsLayoutBuilder builder = new WeatherIconsLayoutBuilder(forecast, context);
        return builder.buildLayout();
    }

    private LineChart buildChart(Forecast forecast) {
        ChartBuilder builder = new ChartBuilder(forecast, context);
        return builder.buildChart();
    }

}
