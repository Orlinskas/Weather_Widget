package com.orlinskas.weatherwidget.ui.main.mvp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.ToastBuilder;

import java.util.Objects;

import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.CENTER_HORIZONTAL;

public class WidgetFragment extends Fragment implements WidgetContract.View, BottomMenu {
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
        addViewButton();
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
    public void finish() {
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        Objects.requireNonNull(getActivity()).finish();
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroy();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void addViewButton() {
        RelativeLayout view = (RelativeLayout) getView();

        RelativeLayout layout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, 180);
        params.setMargins(17,17,17, 17);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.setLayoutParams(params);
        layout.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(10,10,10,10);
        buttonParams.setLayoutDirection(CENTER_HORIZONTAL);

        Button button = new Button(getContext());
        button.setLayoutParams(buttonParams);
        button.setText("Delete");
        button.setBackgroundColor(this.getResources().getColor(R.color.colorAccentDuo));

        final Animation buttonClickAnim = AnimationUtils.loadAnimation(getContext(), R.anim.animation_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClickAnim);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Подтверждение");  // заголовок
                dialog.setMessage("Удалить виджет?"); // сообщение
                dialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        presenter.removeWidget(widgetID);
                    }
                });
                dialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        layout.addView(button);

        if (view != null) {
            try {
                view.addView(layout);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}