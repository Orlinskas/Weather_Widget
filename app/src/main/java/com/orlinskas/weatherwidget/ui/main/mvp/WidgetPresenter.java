package com.orlinskas.weatherwidget.ui.main.mvp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.orlinskas.weatherwidget.chart.ChartBuilder;
import com.orlinskas.weatherwidget.chart.WeatherIconsLayoutBuilder;
import com.orlinskas.weatherwidget.forecast.Forecast;
import com.orlinskas.weatherwidget.forecast.InstrumentPerformance;
import com.orlinskas.weatherwidget.widget.WidgetRemover;
import com.orlinskas.weatherwidget.widget.WidgetUpdateChecker;
import com.orlinskas.weatherwidget.widget.Widget;
import com.orlinskas.weatherwidget.widget.WidgetRepository;

public class WidgetPresenter implements WidgetContract.Presenter, WidgetUpdateListener {
    private WidgetContract.View view;
    private WidgetContract.WidgetModel model;
    private int widgetID;
    private Widget widget;
    private Context viewContext;
    private Context appContext;
    private int dayNumber;
    private int dayCount;

    WidgetPresenter(WidgetContract.View view, int widgetID, Context viewContext, Context appContext) {
        this.viewContext = viewContext;
        this.appContext = appContext;
        this.view = view;
        this.widgetID = widgetID;
    }

    @Override
    public void startWork() {
        widget = findWidgetInRepo(widgetID);
        model = new WidgetModel(this);

        if(widget.getDaysForecast() == null) {
            goWithEmptyData(widgetID, viewContext);
        }
        else {
            goWithData();
        }
    }

    private Widget findWidgetInRepo(int widgetID) {
        Widget widget = null;
        WidgetRepository repository = new WidgetRepository(viewContext);
        try {
            widget = repository.find(widgetID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return widget;
    }

    private void goWithEmptyData(int widgetID, Context appContext) {
        model.doUpdate(widgetID, appContext);
        view.startProgressDialog();
    }

    private void goWithData() {
        dayNumber = 0;
        dayCount = this.widget.getDaysForecast().size() - 1;
        showViewElements();
    }

    private void showViewElements() {
        view.updateUI();
        if(checkAvailableUpdate(widgetID)) {
            startUpdate();
        }
    }

    private boolean checkAvailableUpdate(int widgetID) {
        WidgetUpdateChecker checker = new WidgetUpdateChecker(widgetID, viewContext);
        return checker.check();
    }

    private void startUpdate() {
        int sendWidgetID = widget.getId();
        String cityName = widget.getCity().getName();

        if(isInternetConnection(viewContext)) {
            view.doSnackBar(cityName + " - обновляется...");
            model.doUpdate(sendWidgetID, appContext);
        }
        else {
            view.doToast("Выключен интернет");
        }
    }

    private boolean isInternetConnection(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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
        setButtonAlpha();
        return widget.getDaysForecast().get(dayNumber);
    }

    private void setButtonAlpha() {
        if(dayNumber == 0) {
            view.setAlpha(view.LEFT_BUTTON, view.LOW_MODE);
        }
        else if(dayNumber == dayCount) {
            view.setAlpha(view.RIGHT_BUTTON, view.LOW_MODE);
        }
        else {
            view.setAlpha(view.RIGHT_BUTTON, view.DEFAULT_MODE);
            view.setAlpha(view.LEFT_BUTTON, view.DEFAULT_MODE);
        }
    }

    @Override
    public String getChartDescription() {
        String countryCode = widget.getCity().getCountryCode();
        String cityName = widget.getCity().getName();
        int timeZone = widget.getDaysForecast().get(dayNumber).getDayWeathers().get(0).getTimezone();
        String timeZoneFormat;
        if(timeZone > 0) {
            timeZoneFormat = "UTC +" + timeZone;
        }
        else {
            timeZoneFormat = "UTC " + timeZone;
        }

        return countryCode + "  " + cityName + "  " + timeZoneFormat;
    }

    @Override
    public InstrumentPerformance getInstrumentPerformance() {
        return null;
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
    public void help() {

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
    public void onUpdateFinished() {
        this.widget = findWidgetInRepo(widgetID);
        view.stopProgressDialog();
        view.doSnackBar("Обновлено");
        dayNumber = 0;
        dayCount = this.widget.getDaysForecast().size() - 1;
        showViewElements();
    }

    @Override
    public void onUpdateFailed() {
        view.doSnackBar("Ошибка получения данных");
    }

    @Override
    public void removeWidget(int widgetID) {
        WidgetRemover remover = new WidgetRemover(viewContext);
        remover.remove(widgetID);
        view.finish();
    }

    @Override
    public void destroy() {
        view = null;
        model = null;
        widget = null;
    }
}
