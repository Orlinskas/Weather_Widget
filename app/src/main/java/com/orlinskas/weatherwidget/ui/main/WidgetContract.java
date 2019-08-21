package com.orlinskas.weatherwidget.ui.main;

import android.content.Context;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.orlinskas.weatherwidget.forecast.Forecast;
import com.orlinskas.weatherwidget.widget.Widget;

public interface WidgetContract {

    interface View {
        void setChart(LineChart chart);
        void setIconsLayout(LinearLayout linearLayout);
        void updateUI();
        void doToast(String message);
        void doSnackBar(String message);
        void startProgressDialog();
        void stopProgressDialog();
    }

    interface Presenter {
        LinearLayout getIconsLayout();
        LineChart getChartLayout();
        String getChartDescription();
        String getCurrentDate();
        boolean nextDay();
        boolean prevDay();
        void destroy();
        boolean checkAvailableUpdate(Widget widget);
        void onUpdateFinished();
    }

    interface WidgetModel {
        void doUpdate(Widget widget, Context appContext);
    }
}
