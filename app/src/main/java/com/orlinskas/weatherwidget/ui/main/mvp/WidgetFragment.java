package com.orlinskas.weatherwidget.ui.main.mvp;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.ToastBuilder;

import java.util.Objects;

public class WidgetFragment extends Fragment implements WidgetContract.View {
    private int widgetID;
    private ProgressBar progressBar;
    private RelativeLayout iconsLayoutCase;
    private RelativeLayout chartLayoutCase;
    private ImageView leftBtn;
    private ImageView rightBtn;
    private TextView currentDateTV, chartDescriptionTV;
    private WidgetContract.Presenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_widget, container, false);
        progressBar = root.findViewById(R.id.fragment_widget_pb);
        iconsLayoutCase = root.findViewById(R.id.fragment_widget_rl_icons_case);
        chartLayoutCase = root.findViewById(R.id.fragment_widget_rl_chart_case);
        //ImageButton prevDayBtn = root.findViewById(R.id.fragment_widget_btn_left);
        //ImageButton nextDayBtn = root.findViewById(R.id.fragment_widget_btn_right);
        leftBtn = root.findViewById(R.id.fragment_widget_iv_left);
        rightBtn = root.findViewById(R.id.fragment_widget_iv_right);
        chartDescriptionTV = root.findViewById(R.id.fragment_widget_tv_description);
        currentDateTV = root.findViewById(R.id.fragment_widget_tv_date);

        widgetID = getWidgetIDArgument();

        final Animation buttonClickAnim = AnimationUtils.loadAnimation(getContext(), R.anim.animation_button);

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(presenter.prevDay()){
                    v.startAnimation(buttonClickAnim);
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(presenter.nextDay()) {
                    v.startAnimation(buttonClickAnim);
                }
            }
        });

        return root;
    }

    private int getWidgetIDArgument() {
        int id = 0;
        if (getArguments() != null) {
            id = getArguments().getInt("widgetID");
        }
        return id;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new WidgetPresenter(this, widgetID,
                getContext(), Objects.requireNonNull(getActivity()).getBaseContext());
        presenter.startWork();
    }

    @Override
    public void updateUI() {
        setIconsLayout(presenter.getIconsLayout());
        setChart(presenter.getChartLayout());
        setChartDate(presenter.getCurrentDate());
        setChartDescription(presenter.getChartDescription());
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
    public void setAlpha(String itemName, int mode) {
        int alpha = 250;

        switch (mode) {
            case DEFAULT_MODE:
                alpha = 250;
                break;
            case LOW_MODE:
                alpha = 50;
                break;
        }

        switch (itemName) {
            case LEFT_BUTTON:
                leftBtn.setImageAlpha(alpha);
                break;
            case RIGHT_BUTTON:
                rightBtn.setImageAlpha(alpha);
                break;
        }
    }

    @Override
    public void startProgressDialog() {
        iconsLayoutCase.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgressDialog() {
        iconsLayoutCase.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroy();
    }
}