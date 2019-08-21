package com.orlinskas.weatherwidget.ui.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.orlinskas.weatherwidget.chart.ChartBuilder;
import com.orlinskas.weatherwidget.chart.WeatherIconsLayoutBuilder;
import com.orlinskas.weatherwidget.forecast.Forecast;
import com.orlinskas.weatherwidget.forecast.WidgetUpdateChecker;
import com.orlinskas.weatherwidget.widget.Widget;
import com.orlinskas.weatherwidget.widget.WidgetModel;

public class WidgetPresenter implements WidgetContract.Presenter {
    private WidgetContract.View view;
    private WidgetContract.WidgetModel model;
    private Widget widget;
    private Context context;
    private Context appContext;
    private int dayNumber;
    private int dayCount;

    WidgetPresenter(Widget widget, Context context, Context appContext) {
        this.widget = widget;
        this.context = context;
        this.appContext = appContext;
        model = new WidgetModel();
        dayNumber = 0;
        dayCount = widget.getDaysForecast().size() - 1;
    }

    @Override
    public void attachView(WidgetContract.View view) {
        this.view = view;
    }

    @Override
    public void viewIsReady() {
        view.updateUI();

        if(checkAvailableUpdate(widget)) {
            if(isInternetConnection(context)) {
                String cityName = widget.getCity().getName();
                view.doSnackBar(cityName + " - обновляется...");
                model.doUpdate(widget, appContext);
            }
            else {
                String cityName = widget.getCity().getName();
                view.doSnackBar(cityName + " - требует обновления, но у вас выключен интернет.");
            }
        }
    }

    @Override
    public Widget getWidget() {
        return widget;
    }

    @Override
    public LinearLayout getIconsLayout() {
        WeatherIconsLayoutBuilder builder = new WeatherIconsLayoutBuilder(getCurrentForecast(), context);
        return builder.buildLayout();
    }

    @Override
    public LineChart getChartLayout() {
        ChartBuilder builder = new ChartBuilder(getCurrentForecast(), context);
        return builder.buildChart();
    }

    @Override
    public String getCurrentDate() {
        return getCurrentForecast().getDayDate();
    }

    private Forecast getCurrentForecast() {
        return widget.getDaysForecast().get(dayNumber);
    }

    @Override
    public String getChartDescription() {
        String countryCode = widget.getCity().getCountryCode();
        String cityName = widget.getCity().getName();
        return countryCode + "  " + cityName;
    }

    @Override
    public boolean prevDay() {
        if(dayNumber > 0){
            dayNumber--;
            view.updateUI();
            return true;
        }
        view.doToast(buildMessageAvailableDates());
        return false;
    }

    @Override
    public boolean nextDay() {
        if(dayNumber < dayCount){
            dayNumber++;
            view.updateUI();
            return true;
        }
        view.doToast(buildMessageAvailableDates());
        return false;
    }

    private String buildMessageAvailableDates() {
        try {
            return String.format(
                    "%1$s %2$s %3$s %4$s.",
                    "Доступно с",
                    widget.getDaysForecast().get(0).getDayDate(),
                    "до",
                    widget.getDaysForecast().get(dayCount).getDayDate()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return "Пока нет данных";
        }
    }

    @Override
    public boolean checkAvailableUpdate(Widget widget) {
        WidgetUpdateChecker checker = new WidgetUpdateChecker();
        return checker.check(widget);
    }

    private boolean isInternetConnection(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void destroy() {
        view = null;
        model = null;
        widget = null;
    }
}
