package com.orlinskas.weatherwidget.ui.main.home;

import android.content.Context;
import android.util.Log;

import com.orlinskas.weatherwidget.forecast.Forecast;
import com.orlinskas.weatherwidget.widget.WidgetRepository;

public class HomeWidgetPresenter implements HomeWidgetContract.Presenter {
    private final String TAG = this.getClass().getName() ;
    private HomeWidgetContract.View view;
    private int widgetID;
    private Context context;
    private int dayNumber;
    private int dayCount;

    public HomeWidgetPresenter(HomeWidgetContract.View view, int widgetID, Context context) {
        this.view = view;
        this.widgetID = widgetID;
        this.context = context;
        Log.d(TAG, "конструктор");
    }

    @Override
    public void startWork() {

    }

    @Override
    public Forecast getForecast(int widgetID) throws Exception {
        WidgetRepository repository = new WidgetRepository(context);
        Log.d(TAG, "форекаст гет");
        return  repository.find(widgetID).getDaysForecast().get(1);
    }

    @Override
    public boolean nextDay() {
        return false;
    }

    @Override
    public boolean prevDay() {
        return false;
    }

    @Override
    public void destroy() {

    }
}
