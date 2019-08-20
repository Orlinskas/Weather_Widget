package com.orlinskas.weatherwidget.ui.main;

import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.orlinskas.weatherwidget.forecast.Forecast;
import com.orlinskas.weatherwidget.widget.Widget;

public interface WidgetContract {

    interface View {
        void setChart(LineChart chart);
        void setIconsLayout(LinearLayout linearLayout);
        void setWidget(Widget widget);
        void updateUI();
        void doToast(String message);
    }

    interface Presenter {
        void attachView(WidgetContract.View view);
        void viewIsReady();
        Widget getWidget();
        LinearLayout getIconsLayout();
        LineChart getChartLayout();
        String getChartDescription();
        String getCurrentDate();
        boolean nextDay();
        boolean prevDay();
        void destroy();
    }

    interface WidgetModel {
        LinearLayout buildIconsLayout(Forecast forecast);
        LineChart buildChartLayout(Forecast forecast);
        Widget update(Widget widget);

    }
}
