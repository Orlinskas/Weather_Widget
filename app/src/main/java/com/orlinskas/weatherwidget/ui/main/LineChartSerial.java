package com.orlinskas.weatherwidget.ui.main;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.github.mikephil.charting.charts.LineChart;

import java.io.Serializable;

public class LineChartSerial implements Serializable {
    private LineChart lineChart;

    public LineChartSerial(LineChart lineChart) {
        this.lineChart = lineChart;
    }
}
