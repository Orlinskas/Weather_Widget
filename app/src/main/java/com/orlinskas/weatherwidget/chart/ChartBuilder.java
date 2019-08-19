package com.orlinskas.weatherwidget.chart;

import android.content.Context;
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
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.forecast.Forecast;
import com.orlinskas.weatherwidget.forecast.Weather;

import java.util.ArrayList;
import java.util.List;

public class ChartBuilder {
    private LineChart chart;
    private Context context;
    private Forecast forecast;

    public ChartBuilder(LineChart chart, Forecast forecast, Context context) {
        this.chart = chart;
        this.context = context;
        this.forecast = forecast;
    }

    public LineChart buildChart() {
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        LineDataSet dataSet = createLineDataSet();
        dataSets.add(dataSet);
        LineData lineData = new LineData(dataSets);
        chart.setData(lineData);

        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);

        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        buildXAxis();
        buildYAxis();

        return chart;
    }

    private LineDataSet createLineDataSet() {
        List<Entry> entries = collectEntries();
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
                    result = result.substring(0, result.indexOf("."));
                }
                return result;
            }
        });

        return dataSet;
    }

    private List<Entry> collectEntries() {
        ArrayList<Weather> weathers = forecast.getDayWeathers();

        List<Entry> entries = new ArrayList<>();

        for(int i = 0; i < weathers.size(); i++) {
            if(weathers.get(i) != null) {
                float temperature = (float) weathers.get(i).getCurrentTemperature();
                entries.add(new Entry(i, temperature));
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
                try {
                    String date = forecast.getDayWeathers().get((int) value).getTimeOfDataForecast();
                    return date.substring(11);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "n/a";
                }
            }
        });
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
}
