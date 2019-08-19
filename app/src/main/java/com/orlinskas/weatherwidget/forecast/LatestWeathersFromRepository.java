package com.orlinskas.weatherwidget.forecast;

import android.content.Context;

import com.orlinskas.weatherwidget.repository.WeatherRepository;
import com.orlinskas.weatherwidget.specification.WeatherWidgetSpecification;
import com.orlinskas.weatherwidget.widget.Widget;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class LatestWeathersFromRepository {
    private Widget widget;
    private Context context;

    public LatestWeathersFromRepository(Widget widget, Context context) {
        this.widget = widget;
        this.context = context;
    }

    public ArrayList<String> getUnique() {
        WeatherRepository repository = new WeatherRepository(context);
        ArrayList<Weather> allWeathersForCity = repository.query(new WeatherWidgetSpecification(widget));

        ArrayList<String> latestUniqueDate = new ArrayList<>(find(allWeathersForCity));

        return latestUniqueDate;
    }

    private LinkedHashSet<String> find(ArrayList<Weather> allWeathersForCity) {
        LinkedHashSet<String> unique = new LinkedHashSet<>();

        for (Weather weather : allWeathersForCity) {
            unique.add(weather.getTimeOfDataForecast().substring(0, 10));
        }

        return unique;
    }
}
