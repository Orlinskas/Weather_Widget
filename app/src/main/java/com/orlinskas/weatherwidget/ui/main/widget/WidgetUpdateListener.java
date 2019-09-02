package com.orlinskas.weatherwidget.ui.main.widget;

public interface WidgetUpdateListener {
    void onUpdateFinished(String name);
    void onUpdateFailed(String message);
}
