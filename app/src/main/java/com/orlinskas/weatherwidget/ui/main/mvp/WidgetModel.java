package com.orlinskas.weatherwidget.ui.main.mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.orlinskas.weatherwidget.date.DateFormat;
import com.orlinskas.weatherwidget.date.DateHelper;
import com.orlinskas.weatherwidget.forecast.ForecastListBuilder;
import com.orlinskas.weatherwidget.forecast.WeatherReceiver;
import com.orlinskas.weatherwidget.preferences.Preferences;
import com.orlinskas.weatherwidget.widget.Widget;
import com.orlinskas.weatherwidget.widget.WidgetRepository;

public class WidgetModel implements WidgetContract.WidgetModel {
    private WidgetUpdateListener presenter;

    WidgetModel(WidgetUpdateListener presenter) {
        this.presenter = presenter;
    }

    @Override
    public void doUpdate(int widgetID, Context appContext) {
        UpdateWidgetTask task = new UpdateWidgetTask(appContext, widgetID, presenter);
        task.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateWidgetTask extends AsyncTask <Void, Void, Void> {
        private Context context;
        private int widgetID;
        private WidgetUpdateListener presenter;
        private Throwable error;

        UpdateWidgetTask(Context context, int widgetID, WidgetUpdateListener presenter) {
            this.context = context;
            this.widgetID = widgetID;
            this.presenter = presenter;
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
                error = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            generateCallBack();
        }

        private void generateCallBack() {
            if(error == null) {
                presenter.onUpdateFinished();
            }
            else {
                presenter.onUpdateFailed();
            }
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

        private void saveWidgetUpdateDate(Widget widget) {
            String key = String.valueOf(widget.getId());
            String currentDate = DateHelper.getCurrent(DateFormat.YYYY_MM_DD_HH_MM);

            Preferences preferences = Preferences.getInstance(context,Preferences.WIDGET_UPDATE_DATES);
            preferences.saveData(key, currentDate);
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
    }
}
