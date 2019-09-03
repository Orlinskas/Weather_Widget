package com.orlinskas.weatherwidget.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.orlinskas.weatherwidget.date.DateFormat;
import com.orlinskas.weatherwidget.date.DateHelper;
import com.orlinskas.weatherwidget.forecast.ForecastListBuilder;
import com.orlinskas.weatherwidget.forecast.WeatherReceiver;
import com.orlinskas.weatherwidget.preferences.Preferences;

class WidgetUpdater {

    void doUpdate(int widgetID, Context appContext) {
        UpdateWidgetTask task = new UpdateWidgetTask(appContext, widgetID);
        task.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateWidgetTask extends AsyncTask<Void, Void, Void> {
        private Context context;
        private int widgetID;

        UpdateWidgetTask(Context context, int widgetID) {
            this.context = context;
            this.widgetID = widgetID;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Widget widget;
            try {
                widget = findWidgetInRepo(widgetID);
                sendRequest(widget);
                updateForecastInWidget(widget);
                updateWidgetInRepository(widget);
                saveWidgetUpdateDate(widget);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        private Widget findWidgetInRepo(int widgetID) {
            Widget widget = null;
            WidgetRepository repository = new WidgetRepository(context);
            try {
                widget = repository.find(widgetID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return widget;
        }

        private void sendRequest(Widget widget) throws Exception {
            WeatherReceiver receiver = new WeatherReceiver(context, widget.getRequest());
            receiver.receive();
        }

        private void updateForecastInWidget(Widget widget) {
            ForecastListBuilder forecastsBuilder = new ForecastListBuilder(widget, context);
            widget.setDaysForecast(forecastsBuilder.build());
        }

        private void updateWidgetInRepository(Widget widget) throws Exception {
            WidgetRepository repository = new WidgetRepository(context);
            repository.update(widget);
        }

        private void saveWidgetUpdateDate(Widget widget) {
            String key = String.valueOf(widget.getId());
            String currentDate = DateHelper.getCurrent(DateFormat.YYYY_MM_DD_HH_MM);

            Preferences preferences = Preferences.getInstance(context,Preferences.WIDGET_UPDATE_DATES);
            preferences.saveData(key, currentDate);
        }
    }
}
