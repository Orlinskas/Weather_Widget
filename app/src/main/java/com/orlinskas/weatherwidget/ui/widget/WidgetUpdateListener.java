package com.orlinskas.weatherwidget.ui.widget;

public interface WidgetUpdateListener {
    void onUpdateFinished(String name);
    void onUpdateFailed(String message);
}
