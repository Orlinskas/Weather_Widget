package com.orlinskas.weatherwidget.ui.main;

import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.orlinskas.weatherwidget.widget.Widget;

public interface WidgetContract {

    interface View {
        void setChart(LineChart chart);
        void setIconsLayout(LinearLayout linearLayout);
        void setWidget(Widget widget);
        void updateUI();
    }

    interface Presenter {
        void attachView(WidgetContract.View view);
        boolean nextDay();
        boolean prevDay();
        Widget getWidget();
        LinearLayout getIconsLayout();
        LineChart getChartLayout();
        String getChartDescription();
        String getCurrentDate();
        void viewIsReady();
    }

    interface Repository {
        String loadMessage();
    }
}
