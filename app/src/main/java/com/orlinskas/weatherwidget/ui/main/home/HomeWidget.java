package com.orlinskas.weatherwidget.ui.main.home;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.forecast.Forecast;
import com.orlinskas.weatherwidget.specification.WidgetEmptySpecification;
import com.orlinskas.weatherwidget.widget.WidgetRepository;

public class HomeWidget extends AppWidgetProvider implements HomeWidgetContract.View {
    private final String TAG = this.getClass().getSimpleName();

    private int widgetID;
    private RelativeLayout parrentLayout;
    private ImageView lefnBtn, rightBtn;
    private HomeWidgetContract.Presenter presenter;

    public HomeWidget() {
        super();
        Log.d(TAG,"конструктор");

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        remoteViews.setTextViewText(R.id.layout_widget_tv_description, "test");

        Log.d(TAG,"enabled");
        WidgetRepository repository = new WidgetRepository(context);
        try {
            widgetID = repository.query(new WidgetEmptySpecification()).get(0).getId();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG,e.getMessage());
        }


        presenter = new HomeWidgetPresenter(this, widgetID, context);
        try {
            setForecast(presenter.getForecast(widgetID), context);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG,e.getMessage());
        }


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        remoteViews.setTextViewText(R.id.layout_widget_tv_description, "test");

        try {
            setForecast(presenter.getForecast(widgetID), context);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG,e.getMessage());
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }

    @Override
    public void setForecast(Forecast forecast, Context context) {
        String date = forecast.getDayDate();

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        remoteViews.setTextViewText(R.id.layout_widget_tv_description, "test");
    }

    @Override
    public void setAlpha(String item, int mode) {

    }

    @Override
    public void updateUI() {

    }
}
