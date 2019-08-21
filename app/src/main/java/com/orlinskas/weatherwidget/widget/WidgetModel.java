package com.orlinskas.weatherwidget.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.orlinskas.weatherwidget.ToastBuilder;
import com.orlinskas.weatherwidget.date.DateFormat;
import com.orlinskas.weatherwidget.date.DateHelper;
import com.orlinskas.weatherwidget.forecast.ForecastArrayBuilder;
import com.orlinskas.weatherwidget.forecast.WeatherReceiver;
import com.orlinskas.weatherwidget.preferences.Preferences;
import com.orlinskas.weatherwidget.ui.main.WidgetContract;

public class WidgetModel implements WidgetContract.WidgetModel {

    @Override
    public void doUpdate(Widget widget, Context appContext) {
        UpdateWidgetTask task = new UpdateWidgetTask(appContext, widget);
        task.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateWidgetTask extends AsyncTask<Void, Void, Void> {
        private Context context;
        private Widget widget;

        UpdateWidgetTask(Context context, Widget widget) {
            this.context = context;
            this.widget = widget;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                sendRequest();
                updateForecastInWidget();
                updateWidgetInRepository();
                saveWidgetUpdateDate();
            } catch (Exception e) {
                e.printStackTrace();
                ToastBuilder.create(context,"Ошибка подключения");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ToastBuilder.createLong(context,"Доступен свежий прогноз, перезапустите приложение");
        }

        private void saveWidgetUpdateDate() {
            String key = String.valueOf(widget.getId());
            String currentDate = DateHelper.getCurrent(DateFormat.YYYY_MM_DD_HH_MM);

            Preferences preferences = Preferences.getInstance(context,Preferences.WIDGET_UPDATE_DATES);
            preferences.saveData(key, currentDate);
        }

        private void sendRequest() throws Exception {
            WeatherReceiver receiver = new WeatherReceiver(context, widget.getRequest());
            receiver.receive();
        }

        private void updateForecastInWidget() throws Exception {
            ForecastArrayBuilder forecastsBuilder = new ForecastArrayBuilder(widget, context);
            widget.setDaysForecast(forecastsBuilder.process());
        }

        private void updateWidgetInRepository() throws Exception {
            WidgetRepository repository = new WidgetRepository(context);
            repository.update(widget);
        }
    }
}
