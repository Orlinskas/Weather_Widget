package com.orlinskas.weatherwidget.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.forecast.ForecastOneDay;
import com.orlinskas.weatherwidget.widget.Widget;

public class WidgetFragment extends Fragment implements WidgetObserver {
    private Widget widget;
    private TextView textView1, textView2;
    private ForecastOneDay forecastOneDay;

    public WidgetFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_widget, container, false);
        getFragmentArgument();

        textView1 = root.findViewById(R.id.textView);
        textView2 = root.findViewById(R.id.textView2);

        textView1.setText("широта лат " + widget.getCity().getCoordLat());
        textView2.setText("долгота лон " + widget.getCity().getCoordLon());

        return root;
    }

    private void getFragmentArgument() {
        if (getArguments() != null) {
            widget = (Widget) getArguments().getSerializable("widget");
        }
    }

    @Override
    public void update() {

    }

}