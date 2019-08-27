package com.orlinskas.weatherwidget.ui.main.home;

import android.content.Context;

import com.orlinskas.weatherwidget.forecast.Forecast;

public interface HomeWidgetContract {

    interface View {
        void setForecast(Forecast forecast, Context context);
        void setAlpha(String item, int mode);
        void updateUI();
        String LEFT_BUTTON = "left";
        String RIGHT_BUTTON = "right";
        int DEFAULT_MODE = 0;
        int LOW_MODE = 1;
    }

    interface Presenter {
        void startWork();
        Forecast getForecast(int widgetID) throws Exception;
        boolean nextDay();
        boolean prevDay();
        void destroy();
    }

    interface Model {

    }
}
