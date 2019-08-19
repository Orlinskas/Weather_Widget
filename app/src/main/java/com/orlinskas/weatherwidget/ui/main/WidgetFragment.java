package com.orlinskas.weatherwidget.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.ToastBuilder;
import com.orlinskas.weatherwidget.chart.ChartBuilder;
import com.orlinskas.weatherwidget.chart.WeatherIconsLayoutBuilder;
import com.orlinskas.weatherwidget.forecast.ForecastFiveDayRepositoryGetter;
import com.orlinskas.weatherwidget.forecast.ForecastOneDay;
import com.orlinskas.weatherwidget.forecast.ForecastReceiver;
import com.orlinskas.weatherwidget.forecast.WidgetUpdateChecker;
import com.orlinskas.weatherwidget.widget.Widget;
import com.orlinskas.weatherwidget.widget.WidgetRepository;

public class WidgetFragment extends Fragment implements WidgetObserver {
    private Widget widget;
    private ProgressBar progressBar;
    private LinearLayout weatherIconLayout;
    private TextView currentDate, chartLabelDescription;
    private ImageButton prevDay, nextDay;
    private LineChart lineChart;
    private int dayNumber;

    public WidgetFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_widget, container, false);
        progressBar = root.findViewById(R.id.fragment_widget_pb);
        currentDate = root.findViewById(R.id.fragment_widget_tv_date);
        weatherIconLayout = root.findViewById(R.id.fragment_widget_ll_weather_icon);
        prevDay = root.findViewById(R.id.fragment_widget_btn_left);
        nextDay = root.findViewById(R.id.fragment_widget_btn_right);
        lineChart = root.findViewById(R.id.fragment_widget_chart);
        chartLabelDescription = root.findViewById(R.id.fragment_widget_tv_description);

        dayNumber = 0;

        getFragmentArgument();

        final Animation buttonClickAnim = AnimationUtils.loadAnimation(getContext(), R.anim.animation_button);

        prevDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dayNumber > 0) {
                    v.startAnimation(buttonClickAnim);
                    dayNumber--;
                    updateUI(dayNumber);
                }
            }
        });

        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dayNumber < 4) {
                    v.startAnimation(buttonClickAnim);
                    dayNumber++;
                    updateUI(dayNumber);
                }
            }
        });

        updateUI(dayNumber);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(checkNeedUpdate()){
            update();
        }
    }

    private void getFragmentArgument() {
        if (getArguments() != null) {
            widget = (Widget) getArguments().getSerializable("widget");
        }
    }

    private boolean checkNeedUpdate() {
        WidgetUpdateChecker updateChecker = new WidgetUpdateChecker();
        return updateChecker.check(widget);
    }

    @Override
    public void update() {
        UpdateWidgetTask updateWidgetTask = new UpdateWidgetTask(getContext(), widget);
        updateWidgetTask.execute();
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    private void updateUI(int day) {
        if(widget.getForecastFiveDay() != null) {
            ForecastOneDay forecast = widget.getForecastFiveDay().getDays()[day];
            currentDate.setText(forecast.getDayDate());

            String description = widget.getCity().getCountryCode() + "  " + widget.getCity().getName();
            chartLabelDescription.setText(description);

            lineChart.clear();
            lineChart = buildChart(day);

            weatherIconLayout = buildLinearLayout(day);

            setButtonAlpha();
        }
    }

    private LinearLayout buildLinearLayout(int day) {
        WeatherIconsLayoutBuilder builder = new WeatherIconsLayoutBuilder(weatherIconLayout,widget, getContext());
        return builder.buildLayout(day);
    }

    private LineChart buildChart(int day) {
        ChartBuilder builder = new ChartBuilder(lineChart, widget, getContext());
        return builder.buildChart(day);
    }

    private void setButtonAlpha() {
        if(dayNumber == 0) {
            prevDay.setAlpha(0.3f);
        }
        else {
            prevDay.setAlpha(1.0f);
        }
        if(dayNumber == 4) {
            nextDay.setAlpha(0.3f);
        }
        else {
            nextDay.setAlpha(1.0f);
        }
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
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            weatherIconLayout.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                sendRequest();
                updateForecastInWidget();
                updateWidgetInRepository(widget);
            } catch (Exception e) {
                e.printStackTrace();
                ToastBuilder.create(getContext(), "Не удалось получить данные");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setWidget(widget);
            updateUI(dayNumber);
            progressBar.setVisibility(View.INVISIBLE);
            weatherIconLayout.setVisibility(View.VISIBLE);
        }

        private void sendRequest() throws Exception {
            ForecastReceiver receiver = new ForecastReceiver(context, widget);
            receiver.receive();
        }

        private void updateForecastInWidget() throws Exception {
            ForecastFiveDayRepositoryGetter fiveDayRepositoryGetter = new ForecastFiveDayRepositoryGetter(widget, context);
            widget.setForecastFiveDay(fiveDayRepositoryGetter.process());
        }

        private void updateWidgetInRepository(Widget widget) throws Exception {
            WidgetRepository repository = new WidgetRepository(context);
            repository.update(widget);
        }
    }
}