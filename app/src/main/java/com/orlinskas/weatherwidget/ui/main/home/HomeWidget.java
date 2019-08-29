package com.orlinskas.weatherwidget.ui.main.home;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.chart.WeatherIconsSelector;
import com.orlinskas.weatherwidget.forecast.Forecast;
import com.orlinskas.weatherwidget.forecast.Weather;
import com.orlinskas.weatherwidget.preferences.Preferences;
import com.orlinskas.weatherwidget.widget.Widget;
import com.orlinskas.weatherwidget.widget.WidgetRepository;

import java.util.ArrayList;
import java.util.Objects;

public class HomeWidget extends AppWidgetProvider {
    private final String TAG = this.getClass().getSimpleName();
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

    public HomeWidget() {
        super();
        Log.d(TAG,"конструктор");

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        int ID = Objects.requireNonNull(intent.getExtras()).getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
        if(ID != AppWidgetManager.INVALID_APPWIDGET_ID) {
            updateWidget(ID, context, AppWidgetManager.getInstance(context));
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int id : appWidgetIds) {
            updateWidget(id, context, appWidgetManager);
        }

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    public void updateWidget(int id, Context context, AppWidgetManager appWidgetManager) {
        int widgetID = findID(id, context);
        Widget widget = findWidget(widgetID, context);
        RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        if(widget != null){
            Forecast forecast = widget.getDaysForecast().get(0);
            widgetView = updateUI(widgetView, forecast);
        }

        appWidgetManager.updateAppWidget(id, widgetView);
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

                    widgetView.setImageViewResource(R.id.layout_widget_iv_left,R.drawable.ic_left_4);
                    widgetView.setImageViewResource(R.id.layout_widget_iv_right,R.drawable.ic_right_4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                indexView--;
            }

        return widgetView;
    }

    private Widget findWidget(int widgetID, Context context) {
        WidgetRepository repository = new WidgetRepository(context);
        try {
            return repository.find(widgetID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int findID(int id, Context context) {
        Preferences preferences = Preferences.getInstance(context,Preferences.SETTINGS);
        return preferences.getData("Widget" + id, 0);
    }
}
