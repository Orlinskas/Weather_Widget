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
import com.orlinskas.weatherwidget.widget.WidgetsUpdater;

import java.util.ArrayList;

public class HomeWidget extends AppWidgetProvider {
    public static final String ACTION = "action";
    public static final String ACTION_CREATE = "create";
    public static final String ACTION_DEFAULT = "default";
    public static final String ACTION_UPDATE = "update";
    private final String ACTION_CLICK_LEFT = "leftAreaClick";
    private final String ACTION_CLICK_RIGHT = "rightAreaClick";
    private final String ACTION_CLICK_CENTER = "centerAreaClick";

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
            updateWidget(id, context);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        String actionExtra = ACTION_DEFAULT;

        if(intent.hasExtra(ACTION)) {
            actionExtra = intent.getStringExtra(ACTION);
        }

        if(id != AppWidgetManager.INVALID_APPWIDGET_ID) {
            int dayNumber = readDayNumber(id, context);

            switch (actionExtra) {
                case ACTION_CREATE:
                case ACTION_DEFAULT:
                case ACTION_UPDATE:
                    updateWidget(id,context);
                case ACTION_CLICK_CENTER:
                    WidgetsUpdater widgetsUpdater = new WidgetsUpdater(context);
                    widgetsUpdater.update();
                    updateWidget(id,context);
                    break;
                case ACTION_CLICK_LEFT:
                    writeDayNumber(dayNumber - 1, id, context);
                    updateWidget(id, context);
                    break;
                case ACTION_CLICK_RIGHT:
                    writeDayNumber(dayNumber + 1, id, context);
                    updateWidget(id, context);
                    break;
            }
        }
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        Preferences preferences = Preferences.getInstance(context, Preferences.SETTINGS);

        for(int i = 0; i < oldWidgetIds.length; i++) {
            Widget widget = findWidget(oldWidgetIds[i], context);
            if (widget != null) {
                preferences.saveData(Preferences.WIDGET_ID_DEPENDENCE + newWidgetIds[i], widget.getId());
            }
        }
    }

    public void updateWidget(int id, Context context) {
        RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent leftClickIntent = new Intent(context, HomeWidget.class);
        leftClickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        leftClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
        leftClickIntent.putExtra(ACTION, ACTION_CLICK_LEFT);
        PendingIntent pLeftIntent = PendingIntent.getBroadcast(context, id + 100, leftClickIntent, 0);

        Intent rightClickIntent = new Intent(context, HomeWidget.class);
        rightClickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        rightClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
        rightClickIntent.putExtra(ACTION, ACTION_CLICK_RIGHT);
        PendingIntent pRightIntent = PendingIntent.getBroadcast(context, id + 200, rightClickIntent, 0);

        Intent centerClickIntent = new Intent(context, HomeWidget.class);
        centerClickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        centerClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
        centerClickIntent.putExtra(ACTION, ACTION_CLICK_CENTER);
        PendingIntent pCenterIntent = PendingIntent.getBroadcast(context, id + 300, centerClickIntent, 0);

        widgetView.setOnClickPendingIntent(R.id.layout_widget_btn_left_click_area, pLeftIntent);
        widgetView.setOnClickPendingIntent(R.id.layout_widget_btn_right_click_area, pRightIntent);
        widgetView.setOnClickPendingIntent(R.id.layout_widget_btn_center_click_area, pCenterIntent);

        widgetView.setImageViewResource(R.id.layout_widget_iv_left, R.drawable.ic_left_4);
        widgetView.setImageViewResource(R.id.layout_widget_iv_right, R.drawable.ic_right_4);

        AppWidgetManager.getInstance(context).updateAppWidget(id, widgetView);

        Widget widget = findWidget(id, context);

        if(widget != null && widget.getDaysForecast() != null){
            int dayNumber = readDayNumber(id, context);

            if(dayNumber < 0 | dayNumber > widget.getDaysForecast().size() - 1) {
                dayNumber = 0;
                writeDayNumber(dayNumber, id, context);
            }
            Forecast forecast = widget.getDaysForecast().get(dayNumber);
            updateUI(id, forecast, context);
        }
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

    private int readDayNumber(int id, Context context) {
        Preferences preferences = Preferences.getInstance(context, Preferences.SETTINGS);
        return preferences.getData(Preferences.WIDGET_DAY_NUMBER + id, 0);
    }

    private void writeDayNumber(int value, int id, Context context) {
        Preferences preferences = Preferences.getInstance(context, Preferences.SETTINGS);
        if(value < 0) {
            value = 0;
        }
        preferences.saveData(Preferences.WIDGET_DAY_NUMBER + id, value);
    }

    private void updateUI(int id, Forecast forecast, Context context) {
        RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

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
        int indexWeather = weathers.size() - 1;

        for(int indexView = 7; indexView >= 0; indexView--) {
            int ID = R.drawable.ic_na_icon;
            String temperature = "—";
            String dateTime = "—:—";
            try {
                WeatherIconsSelector selector = new WeatherIconsSelector();
                ID = selector.findIcon(weathers.get(indexWeather));

                int temp = weathers.get(indexWeather).getCurrentTemperature();
                temperature = temp + "°";

                dateTime = weathers.get(indexWeather).getTimeOfDataForecast().substring(11);
            } catch (Exception e) {
                e.printStackTrace();
            }
            indexWeather--;

            widgetView.setImageViewResource(imageViewIDsIcons[indexView], ID);
            widgetView.setTextViewText(textViewIDsTemperatures[indexView], temperature);
            widgetView.setTextViewText(textViewIDsDates[indexView], dateTime);
        }
        AppWidgetManager.getInstance(context).updateAppWidget(id, widgetView);
    }
}
