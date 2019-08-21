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
import com.orlinskas.weatherwidget.specification.WidgetCitySpecification;
import com.orlinskas.weatherwidget.widget.Widget;
import com.orlinskas.weatherwidget.widget.WidgetModel;
import com.orlinskas.weatherwidget.widget.WidgetRepository;
import com.orlinskas.weatherwidget.widget.WidgetUpdateListener;

import java.util.ArrayList;

public class WidgetPresenter implements WidgetContract.Presenter, WidgetUpdateListener {
    private WidgetContract.View view;
    private WidgetContract.WidgetModel model;
    private Widget widget;
    private Context viewContext;
    private Context appContext;
    private int dayNumber;
    private int dayCount;

    WidgetPresenter(WidgetContract.View view, Widget widget, Context viewContext, Context appContext) {
        this.view = view;
        this.widget = widget;
        this.viewContext = viewContext;
        this.appContext = appContext;
        model = new WidgetModel(this);

        if(widget.getDaysForecast() == null) {
            goWithEmptyData(widget, viewContext);
        }
        else {
            goWithData();
        }
    }

    private void goWithEmptyData(Widget widget, Context appContext) {
        model.doUpdate(widget, appContext);
        view.startProgressDialog();
    }

    private void goWithData() {
        dayNumber = 0;
        dayCount = this.widget.getDaysForecast().size() - 1;
        startWork();
    }

    private void setWidget(Widget widget) {
        this.widget = widget;
    }

    private void startWork() {
        view.updateUI();

        if(checkAvailableUpdate(widget)) {
            if(isInternetConnection(viewContext)) {
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
    public LinearLayout getIconsLayout() {
        WeatherIconsLayoutBuilder builder = new WeatherIconsLayoutBuilder(getCurrentForecast(), viewContext);
        return builder.buildLayout();
    }

    @Override
    public LineChart getChartLayout() {
        ChartBuilder builder = new ChartBuilder(getCurrentForecast(), viewContext);
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
        WidgetUpdateChecker checker = new WidgetUpdateChecker(widget, viewContext);
        return checker.check();
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

    @Override
    public void onUpdateFinished() {
        setUpdateWidget();
    }

    private void setUpdateWidget() {
        WidgetRepository repository = new WidgetRepository(viewContext);
        try {
            ArrayList<Widget> widgets = repository.query(new WidgetCitySpecification(widget));
            setWidget(widgets.get(0));
            view.stopProgressDialog();
            startWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
