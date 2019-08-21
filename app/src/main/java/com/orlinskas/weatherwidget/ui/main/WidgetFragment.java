package com.orlinskas.weatherwidget.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.orlinskas.weatherwidget.ToastBuilder;
import com.orlinskas.weatherwidget.widget.Widget;

import java.util.Objects;

public class WidgetFragment extends Fragment implements WidgetContract.View {
    private Widget widget;
    private RelativeLayout iconsLayoutCase;
    private RelativeLayout chartLayoutCase;
    private TextView currentDateTV, chartDescriptionTV;
    private WidgetContract.Presenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_widget, container, false);
        iconsLayoutCase = root.findViewById(R.id.fragment_widget_rl_icons_case);
        chartLayoutCase = root.findViewById(R.id.fragment_widget_rl_chart_case);
        ImageButton prevDayBtn = root.findViewById(R.id.fragment_widget_btn_left);
        ImageButton nextDayBtn = root.findViewById(R.id.fragment_widget_btn_right);
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
        presenter = new WidgetPresenter(widget, getContext(), Objects.requireNonNull(getActivity()).getApplicationContext());
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

    @Override
    public void doToast(String message) {
        ToastBuilder.create(getContext(), message);
    }

    @Override
    public void doSnackBar(String message) {
        Snackbar.make(Objects.requireNonNull(getView()), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroy();
    }
}