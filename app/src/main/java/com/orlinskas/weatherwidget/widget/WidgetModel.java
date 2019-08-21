package com.orlinskas.weatherwidget.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.orlinskas.weatherwidget.ToastBuilder;
import com.orlinskas.weatherwidget.forecast.ForecastArrayBuilder;
import com.orlinskas.weatherwidget.forecast.WeatherReceiver;
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
            } catch (Exception e) {
                e.printStackTrace();
                ToastBuilder.create(context,"Ошибка подключения");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ToastBuilder.create(context,"Новые данные получены, перезапустите приложение");
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
