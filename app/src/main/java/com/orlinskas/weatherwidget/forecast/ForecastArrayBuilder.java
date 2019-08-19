package com.orlinskas.weatherwidget.forecast;

import android.content.Context;

import com.orlinskas.weatherwidget.repository.WeatherRepository;
import com.orlinskas.weatherwidget.specification.WeatherDaySpecification;
import com.orlinskas.weatherwidget.widget.Widget;

import java.util.ArrayList;

public class ForecastArrayBuilder {

    private Widget widget;
    private Context context;

    public ForecastArrayBuilder(Widget widget, Context context) {
        this.widget = widget;
        this.context = context;
    }

    public ArrayList<Forecast> process() {
        LatestWeathersFromRepository repository = new LatestWeathersFromRepository(widget,context);
        ArrayList<String> dates = repository.getUnique();
        ArrayList<Forecast> forecasts = new ArrayList<>();

        forecasts.add(createForecast(dates.get(0)));
        forecasts.add(createForecast(dates.get(1)));
        forecasts.add(createForecast(dates.get(2)));
        forecasts.add(createForecast(dates.get(3)));
        forecasts.add(createForecast(dates.get(4)));

        return forecasts;
    }

    private Forecast createForecast(String date) {
        WeatherRepository repository = new WeatherRepository(context);
        ArrayList<Weather> dayWeathers;
        dayWeathers = repository.query(new WeatherDaySpecification(widget, date));

        Forecast forecast = new Forecast(date);
        forecast.setDayWeathers(dayWeathers);

        return forecast;
    }
}
