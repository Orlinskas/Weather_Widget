package com.orlinskas.weatherwidget.widget;

import android.content.Context;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.orlinskas.weatherwidget.chart.ChartBuilder;
import com.orlinskas.weatherwidget.chart.WeatherIconsLayoutBuilder;
import com.orlinskas.weatherwidget.forecast.Forecast;
import com.orlinskas.weatherwidget.ui.main.WidgetContract;

public class WidgetModel implements WidgetContract.WidgetModel {
    private Context context;

    public WidgetModel(Context context) {
        this.context = context;
    }

    @Override
    public LinearLayout buildIconsLayout(Forecast forecast) {
        WeatherIconsLayoutBuilder builder = new WeatherIconsLayoutBuilder(forecast, context);
        return builder.buildLayout();
    }

    @Override
    public LineChart buildChartLayout(Forecast forecast) {
        ChartBuilder builder = new ChartBuilder(forecast, context);
        return builder.buildChart();
    }

    @Override
    public Widget update(Widget widget) {
        return null;
    }
}
