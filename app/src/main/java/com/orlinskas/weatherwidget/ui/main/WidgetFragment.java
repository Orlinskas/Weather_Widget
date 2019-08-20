package com.orlinskas.weatherwidget.ui.main;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.widget.Widget;

public class WidgetFragment extends Fragment implements WidgetContract.View {
    private Widget widget;
    private ProgressBar progressBar;
    private RelativeLayout iconsLayoutCase;
    private RelativeLayout chartLayoutCase;
    private TextView currentDateTV, chartDescriptionTV;
    private ImageButton prevDayBtn, nextDayBtn;
    private WidgetContract.Presenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_widget, container, false);
        progressBar = root.findViewById(R.id.fragment_widget_pb);
        iconsLayoutCase = root.findViewById(R.id.fragment_widget_rl_icons_case);
        chartLayoutCase = root.findViewById(R.id.fragment_widget_rl_chart_case);
        prevDayBtn = root.findViewById(R.id.fragment_widget_btn_left);
        nextDayBtn = root.findViewById(R.id.fragment_widget_btn_right);
        chartDescriptionTV = root.findViewById(R.id.fragment_widget_tv_description);
        currentDateTV = root.findViewById(R.id.fragment_widget_tv_date);

        getFragmentArgument();

        final Animation buttonClickAnim = AnimationUtils.loadAnimation(getContext(), R.anim.animation_button);

        prevDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(presenter.prevDay()){
                    v.startAnimation(buttonClickAnim);
                }
            }
        });

        nextDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(presenter.nextDay()) {
                    v.startAnimation(buttonClickAnim);
                }
            }
        });

        return root;
    }

    private void getFragmentArgument() {
        if (getArguments() != null) {
            widget = (Widget) getArguments().getSerializable("widget");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new WidgetPresenter(widget, getContext());
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @Override
    public void updateUI() {
        setWidget(presenter.getWidget());
        setIconsLayout(presenter.getIconsLayout());
        setChart(presenter.getChartLayout());
        setChartDate(presenter.getCurrentDate());
        setChartDescription(presenter.getChartDescription());
    }

    @Override
    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    @Override
    public void setIconsLayout(LinearLayout linearLayout) {
        iconsLayoutCase.removeAllViews();
        iconsLayoutCase.addView(linearLayout);
    }

    @Override
    public void setChart(LineChart chart) {
        chartLayoutCase.removeAllViews();
        chartLayoutCase.addView(chart);
    }

    private void setChartDate(String currentDate) {
        currentDateTV.setText(currentDate);
    }

    private void setChartDescription(String chartDescription) {
        chartDescriptionTV.setText(chartDescription);
    }

    //private void setButtonAlpha() {
    //    if(dayNumber == 0) {
    //        prevDayBtn.setAlpha(0.3f);
    //    }
    //    else {
    //        prevDayBtn.setAlpha(1.0f);
    //    }
    //    if(dayNumber == 4) {
    //        nextDayBtn.setAlpha(0.3f);
    //    }
    //    else {
    //        nextDayBtn.setAlpha(1.0f);
    //    }
    //}
    //
    //@SuppressLint("StaticFieldLeak")
    //private class UpdateWidgetTask extends AsyncTask<Void, Void, Void> {
    //    private Context context;
    //    private Widget widget;
//
    //    UpdateWidgetTask(Context context, Widget widget) {
    //        this.context = context;
    //        this.widget = widget;
    //    }
//
    //    @Override
    //    protected void onPreExecute() {
    //        super.onPreExecute();
    //        progressBar.setVisibility(View.VISIBLE);
    //        weatherIconLayout.setVisibility(View.INVISIBLE);
    //    }
//
    //    @Override
    //    protected Void doInBackground(Void... voids) {
    //        try {
    //            sendRequest();
    //            updateForecastInWidget();
    //            updateWidgetInRepository(widget);
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            ToastBuilder.create(getContext(), "Не удалось получить данные");
    //        }
    //        return null;
    //    }
//
    //    @Override
    //    protected void onPostExecute(Void aVoid) {
    //        super.onPostExecute(aVoid);
    //        setWidget(widget);
    //        updateUI(dayNumber);
    //        progressBar.setVisibility(View.INVISIBLE);
    //        weatherIconLayout.setVisibility(View.VISIBLE);
    //    }
//
    //    private void sendRequest() throws Exception {
    //        WeatherReceiver receiver = new WeatherReceiver(context, widget);
    //        receiver.receive();
    //    }
//
    //    private void updateForecastInWidget() throws Exception {
    //        ForecastArrayBuilder forecastsBuilder = new ForecastArrayBuilder(widget, context);
    //        widget.setDaysForecast(forecastsBuilder.process());
    //    }
//
    //    private void updateWidgetInRepository(Widget widget) throws Exception {
    //        WidgetRepository repository = new WidgetRepository(context);
    //        repository.update(widget);
    //    }
    //}
}