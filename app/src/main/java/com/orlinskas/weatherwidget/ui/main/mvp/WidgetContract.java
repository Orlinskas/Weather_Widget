package com.orlinskas.weatherwidget.ui.main.mvp;

import android.content.Context;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;

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
        void startWork();
        LinearLayout getIconsLayout();
        LineChart getChartLayout();
        String getChartDescription();
        String getCurrentDate();
        boolean nextDay();
        boolean prevDay();
        void destroy();
    }

    interface WidgetModel {
        void doUpdate(int widgetID, Context appContext);
    }
}
