package com.orlinskas.weatherwidget.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.orlinskas.weatherwidget.forecast.ForecastListBuilder;
import com.orlinskas.weatherwidget.forecast.WeatherReceiver;
import com.orlinskas.weatherwidget.preferences.Preferences;
import com.orlinskas.weatherwidget.ui.main.home.HomeWidget;

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
                sendIntentToUpdate(widgetID);
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

        private void sendIntentToUpdate(int id) {
            Preferences preferences = Preferences.getInstance(context, Preferences.SETTINGS);
            int ID = preferences.getData(Preferences.WIDGET_ID_DEPENDENCE + id, AppWidgetManager.INVALID_APPWIDGET_ID);

            Intent update = new Intent(context, HomeWidget.class);
            update.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, ID);
            update.putExtra(HomeWidget.ACTION, HomeWidget.ACTION_UPDATE);
            PendingIntent pRightIntent = PendingIntent.getBroadcast(context, id + 400, update, 0);
            try {
                pRightIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }
}
