package com.orlinskas.weatherwidget.chart;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.orlinskas.weatherwidget.forecast.Weather;
import com.orlinskas.weatherwidget.widget.Widget;

import java.util.ArrayList;

public class WeatherIconsLayoutBuilder {
    private LinearLayout layout;
    private Widget widget;
    private Context context;

    public WeatherIconsLayoutBuilder(LinearLayout layout, Widget widget, Context context) {
        this.layout = layout;
        this.widget = widget;
        this.context = context;
    }

    public LinearLayout buildLayout(int day) {
        ArrayList<Weather> weathers = widget.getDaysForecast().get(day).getDayWeathers();
        WeatherIconsSelector iconsManager = new WeatherIconsSelector();
        ArrayList<Integer> icons = iconsManager.select(weathers);

        LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsImage.weight = 1.0f;

        layout.removeAllViews();

        for (int i = 0; i < icons.size(); i++) {
            ImageView iconImage = new ImageView(context);
            iconImage.setLayoutParams(paramsImage);
            iconImage.setImageResource(icons.get(i));
            layout.addView(iconImage);
        }

        return layout;
    }
}
