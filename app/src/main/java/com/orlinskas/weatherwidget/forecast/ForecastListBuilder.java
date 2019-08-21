package com.orlinskas.weatherwidget.forecast;

import android.content.Context;

import com.orlinskas.weatherwidget.date.DateFormat;
import com.orlinskas.weatherwidget.date.DateHelper;
import com.orlinskas.weatherwidget.repository.WeatherRepository;
import com.orlinskas.weatherwidget.specification.WeatherDaySpecification;
import com.orlinskas.weatherwidget.widget.Widget;

import java.util.ArrayList;

public class ForecastListBuilder {

    private Widget widget;
    private Context context;

    public ForecastListBuilder(Widget widget, Context context) {
        this.widget = widget;
        this.context = context;
    }

    public ArrayList<Forecast> build() {
        LatestDatesListBuilder list = new LatestDatesListBuilder(widget, context);
        ArrayList<String> dates = list.build();
        ArrayList<Forecast> forecasts = new ArrayList<>();

        int todayIndex = findToday(dates);

        forecasts.add(createForecast(dates.get(todayIndex)));
        forecasts.add(createForecast(dates.get(todayIndex + 1)));
        forecasts.add(createForecast(dates.get(todayIndex + 2)));
        forecasts.add(createForecast(dates.get(todayIndex + 3)));
        try {
            forecasts.add(createForecast(dates.get(todayIndex + 4)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return forecasts;
    }

    private int findToday(ArrayList<String> dates) {
        String todayDate = DateHelper.getCurrent(DateFormat.YYYY_MM_DD);

        for (String date : dates) {
            if(date.equals(todayDate)){
                return dates.indexOf(date);
            }
        }

        return dates.size() - 4;
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
