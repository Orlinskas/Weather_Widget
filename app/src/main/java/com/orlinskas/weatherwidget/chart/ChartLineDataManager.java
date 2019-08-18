package com.orlinskas.weatherwidget.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.forecast.Weather;
import com.orlinskas.weatherwidget.widget.Widget;

import java.util.ArrayList;
import java.util.List;

public class ChartLineDataManager {
    private Widget widget;
    private String label;
    private String[] dates;
    private LineChart chart;
    private Context context;

    private final String TIME_03_00 = "03:00";
    private final String TIME_06_00 = "06:00";
    private final String TIME_09_00 = "09:00";
    private final String TIME_12_00 = "12:00";
    private final String TIME_15_00 = "15:00";
    private final String TIME_18_00 = "18:00";
    private final String TIME_21_00 = "21:00";
    private final String TIME_00_00 = "00:00";

    public ChartLineDataManager(LineChart chart, Widget widget, Context context) {
        this.widget = widget;
        dates = new String[]{TIME_03_00, TIME_06_00, TIME_09_00, TIME_12_00, TIME_15_00, TIME_18_00,
                TIME_21_00, TIME_00_00};
        this.chart = chart;
        this.context = context;
    }

    public LineChart getLineChart(int day) {
        label = widget.getCity().getCountryCode() + "  " +  widget.getCity().getName();

        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        LineDataSet dataSet = getLineDataSet(day);
        dataSets.add(dataSet);
        LineData lineData = new LineData(dataSets);
        chart.setData(lineData);

        Description description = new Description();
        description.setEnabled(false);
        //description.setText(label);
        //description.setTextAlign(Paint.Align.RIGHT);
        //description.setTextSize(16f);
        //description.setTextColor(context.getResources().getColor(R.color.colorAccentDuo));
        chart.setDescription(description);

        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        buildXAxis();
        buildYAxis();

        return chart;
    }

    private void buildYAxis() {
        Typeface typeface = Typeface.defaultFromStyle(Typeface.BOLD);

        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yAxisLeft.setTextSize(5f);
        yAxisLeft.setTypeface(typeface);
        yAxisLeft.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        });

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yAxisRight.setTextSize(5f);
        yAxisRight.setTypeface(typeface);
        yAxisRight.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        });

    }

    private LineDataSet getLineDataSet(int day){
        List<Entry> entries = getEntries(day);
        LineDataSet dataSet = new LineDataSet(entries, null);

        dataSet.setMode(LineDataSet.Mode.LINEAR);
        dataSet.setFillAlpha(100);
        dataSet.setLineWidth(3f);
        dataSet.setCircleRadius(5f);
        dataSet.setValueTextSize(15f);
        dataSet.setCubicIntensity(3f);

        Typeface typeface = Typeface.defaultFromStyle(Typeface.BOLD);
        dataSet.setValueTypeface(typeface);

        dataSet.setColor(context.getResources().getColor(R.color.colorAccentDuo));
        dataSet.setCircleColor(context.getResources().getColor(R.color.colorAccent));
        dataSet.setCircleHoleColor(context.getResources().getColor(R.color.colorTextBlack));
        dataSet.setValueTextColor(context.getResources().getColor(R.color.colorTextBlack));

        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                String result = Float.toString(value);
                if(result.contains(".")) {
                    result = result.substring(0,result.indexOf("."));
                }
                return result;
            }
        });

        return dataSet;
    }

    private List<Entry> getEntries(int day) {
        ArrayList<Weather> weathers = widget.getForecastFiveDay().getDays()[day].getDayWeathers();

        List<Entry> entries = new ArrayList<>();

        for(Weather weather : weathers) {
            if(weather != null) {
                int index = weathers.indexOf(weather);
                float temperature = (float) weathers.get(index).getCurrentTemperature();

                entries.add(new Entry(index, temperature));
            }
        }

        return entries;
    }

    private void buildXAxis() {
        XAxis xAxis = chart.getXAxis();
        Typeface typeface = Typeface.defaultFromStyle(Typeface.BOLD);
        xAxis.setTypeface(typeface);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(context.getResources().getColor(R.color.colorTextBlack));
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return dates[(int) value];
            }
        });
    }
}
