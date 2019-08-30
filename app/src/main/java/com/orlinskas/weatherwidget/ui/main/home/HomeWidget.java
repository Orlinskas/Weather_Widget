package com.orlinskas.weatherwidget.ui.main.home;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.chart.WeatherIconsSelector;
import com.orlinskas.weatherwidget.forecast.Forecast;
import com.orlinskas.weatherwidget.forecast.Weather;
import com.orlinskas.weatherwidget.preferences.Preferences;
import com.orlinskas.weatherwidget.widget.Widget;
import com.orlinskas.weatherwidget.widget.WidgetRepository;

import java.util.ArrayList;

public class HomeWidget extends AppWidgetProvider {
    private final String ACTION_CLICK_LEFT = "leftAreaClick";
    private final String ACTION_CLICK_RIGHT = "rightAreaClick";
    private final String ACTION_DEFAULT = "default";
    private final String ACTION_NAME = "action";
    private final int[] imageViewIDsIcons = new int[]
            {R.id.layout_widget_ll_chart_case_icon_1, R.id.layout_widget_ll_chart_case_icon_2,
                    R.id.layout_widget_ll_chart_case_icon_3, R.id.layout_widget_ll_chart_case_icon_4,
                    R.id.layout_widget_ll_chart_case_icon_5, R.id.layout_widget_ll_chart_case_icon_6,
                    R.id.layout_widget_ll_chart_case_icon_7, R.id.layout_widget_ll_chart_case_icon_8};
    private final int[] textViewIDsTemperatures = new int[]
            {R.id.layout_widget_ll_chart_case_temp_1, R.id.layout_widget_ll_chart_case_temp_2,
             R.id.layout_widget_ll_chart_case_temp_3, R.id.layout_widget_ll_chart_case_temp_4,
             R.id.layout_widget_ll_chart_case_temp_5, R.id.layout_widget_ll_chart_case_temp_6,
             R.id.layout_widget_ll_chart_case_temp_7, R.id.layout_widget_ll_chart_case_temp_8};
    private final int[] textViewIDsDates = new int[]
            {R.id.layout_widget_ll_chart_case_date_1, R.id.layout_widget_ll_chart_case_date_2,
                    R.id.layout_widget_ll_chart_case_date_3, R.id.layout_widget_ll_chart_case_date_4,
                    R.id.layout_widget_ll_chart_case_date_5, R.id.layout_widget_ll_chart_case_date_6,
                    R.id.layout_widget_ll_chart_case_date_7, R.id.layout_widget_ll_chart_case_date_8};

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int id : appWidgetIds) {
            updateWidget(id, context, appWidgetManager);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String actionAppWidget = intent.getAction();
        int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        String actionExtra = ACTION_DEFAULT;

        if(intent.hasExtra(ACTION_NAME)) {
            actionExtra = intent.getStringExtra(ACTION_NAME);
        }

        if(id != AppWidgetManager.INVALID_APPWIDGET_ID) {
            int dayNumber = readDayNumber(id, context);

            switch (actionExtra) {
                case ACTION_CLICK_LEFT:
                    writeDayNumber(dayNumber - 1, id, context);
                    updateWidget(id, context, AppWidgetManager.getInstance(context));
                    break;
                case ACTION_CLICK_RIGHT:
                    writeDayNumber(dayNumber + 1, id, context);
                    updateWidget(id, context, AppWidgetManager.getInstance(context));
                    break;
                case ACTION_DEFAULT:
                    updateWidget(id, context, AppWidgetManager.getInstance(context));
                    break;
            }
        }
    }

    public void updateWidget(int id, Context context, AppWidgetManager appWidgetManager) {
        Widget widget = findWidget(id, context);
        RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        if(widget != null){
            int dayNumber = readDayNumber(id, context);

            if(dayNumber < 0 | dayNumber > widget.getDaysForecast().size() - 1) {
                dayNumber = 0;
                writeDayNumber(dayNumber, id, context);
            }

            Forecast forecast = widget.getDaysForecast().get(dayNumber);
            widgetView = updateUI(widgetView, forecast);

            Intent leftClickIntent = new Intent(context, HomeWidget.class);
            leftClickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            leftClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
            leftClickIntent.putExtra(ACTION_NAME, ACTION_CLICK_LEFT);
            PendingIntent pLeftIntent = PendingIntent.getBroadcast(context, id + 100, leftClickIntent, 0);

            Intent rightClickIntent = new Intent(context, HomeWidget.class);
            rightClickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            rightClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
            rightClickIntent.putExtra(ACTION_NAME, ACTION_CLICK_RIGHT);
            PendingIntent pRightIntent = PendingIntent.getBroadcast(context, id + 200, rightClickIntent, 0);

            widgetView.setOnClickPendingIntent(R.id.layout_widget_btn_left_click_area, pLeftIntent);
            widgetView.setOnClickPendingIntent(R.id.layout_widget_btn_right_click_area, pRightIntent);
            widgetView.setImageViewResource(R.id.layout_widget_iv_left, R.drawable.ic_left_4);
            widgetView.setImageViewResource(R.id.layout_widget_iv_right, R.drawable.ic_right_4);
        }

        appWidgetManager.updateAppWidget(id, widgetView);
    }

    private void writeDayNumber(int value, int id, Context context) {
        Preferences preferences = Preferences.getInstance(context, Preferences.SETTINGS);
        if(value < 0) {
            value = 0;
        }
        preferences.saveData(Preferences.WIDGET_DAY_NUMBER + id, value);
    }

    private int readDayNumber(int id, Context context) {
        Preferences preferences = Preferences.getInstance(context, Preferences.SETTINGS);
        return preferences.getData(Preferences.WIDGET_DAY_NUMBER + id, 0);
    }

    private RemoteViews updateUI(RemoteViews widgetView, Forecast forecast) {
        String cityName = forecast.getDayWeathers().get(0).getCityName();

        String timezoneUTC;
        int timezone = forecast.getDayWeathers().get(0).getTimezone();

        if(timezone > 0) {
            timezoneUTC = "UTC +" + timezone;
        }
        else {
            timezoneUTC = "UTC " + timezone;
        }

        String description = cityName + "  " + timezoneUTC;
        String todayDate = forecast.getDayDate();
        widgetView.setTextViewText(R.id.layout_widget_tv_description, description);
        widgetView.setTextViewText(R.id.layout_widget_tv_date, todayDate);

        ArrayList<Weather> weathers = forecast.getDayWeathers();

            int indexView = 7;
            for(int i = weathers.size(); i > 0; i--) {
                try {
                    WeatherIconsSelector selector = new WeatherIconsSelector();
                    int ID = selector.findIcon(weathers.get(i - 1));
                    String temperature = weathers.get(i - 1).getCurrentTemperature() + "°C";
                    String dateTime = weathers.get(i - 1).getTimeOfDataForecast().substring(11);

                    widgetView.setImageViewResource(imageViewIDsIcons[indexView], ID);
                    widgetView.setTextViewText(textViewIDsTemperatures[indexView], temperature);
                    widgetView.setTextViewText(textViewIDsDates[indexView], dateTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                indexView--;
            }

        return widgetView;
    }

    private Widget findWidget(int id, Context context) {
        Preferences preferences = Preferences.getInstance(context, Preferences.SETTINGS);
        int widgetID = preferences.getData(Preferences.WIDGET_ID_DEPENDENCE + id, 0);

        WidgetRepository repository = new WidgetRepository(context);
        try {
            return repository.find(widgetID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
