package com.orlinskas.weatherwidget.ui.main;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.mikephil.charting.charts.LineChart;

public class LineChartParcel implements Parcelable {
    private LineChartSerial lineChartSerial;

    public LineChartParcel(LineChartSerial lineChartSerial) {
        this.lineChartSerial = lineChartSerial;
    }

    protected LineChartParcel(Parcel in) {
        lineChartSerial = (LineChartSerial) in.readSerializable();
    }

    public static final Creator<LineChartParcel> CREATOR = new Creator<LineChartParcel>() {
        @Override
        public LineChartParcel createFromParcel(Parcel in) {
            return new LineChartParcel(in);
        }

        @Override
        public LineChartParcel[] newArray(int size) {
            return new LineChartParcel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(lineChartSerial);
    }
}
