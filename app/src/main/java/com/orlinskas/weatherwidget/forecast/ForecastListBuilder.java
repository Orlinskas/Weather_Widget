package com.orlinskas.weatherwidget.forecast;

import android.content.Context;

import com.orlinskas.weatherwidget.date.DateFormat;
import com.orlinskas.weatherwidget.date.DateHelper;
import com.orlinskas.weatherwidget.repository.WeatherRepository;
import com.orlinskas.weatherwidget.specification.WeatherDaySpecification;
import com.orlinskas.weatherwidget.widget.Widget;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;

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

        ArrayList<Weather> dayWeathers = repository.query(new WeatherDaySpecification(widget, date));
        LinkedHashSet<String> uniqueTimeWeathers = new LinkedHashSet<>();

        for(Weather weather : dayWeathers) {
            String time = weather.getTimeOfDataForecast();
            uniqueTimeWeathers.add(time);
        }

        ArrayList<Weather> finalUniqueWeathers = new ArrayList<>();

        for(String time : uniqueTimeWeathers) {
            Weather weather = findLatestWeather(time, dayWeathers);
            if(weather != null) {
                finalUniqueWeathers.add(weather);
            }
        }

        Forecast forecast = new Forecast(date);
        forecast.setDayWeathers(finalUniqueWeathers);

        return forecast;
    }

    private Weather findLatestWeather(String time, ArrayList<Weather> dayWeathers) {
        ArrayList<Weather> timeWeathers = new ArrayList<>();

        for(Weather weather : dayWeathers) {
            if(weather.getTimeOfDataForecast().equals(time)) {
                timeWeathers.add(weather);
            }
        }

        Weather latestWeather = null;

        try {
            latestWeather = timeWeathers.get(0);

            for(Weather weather : timeWeathers) {
                Date current = DateHelper.parse(latestWeather.getForecastDate(), DateFormat.YYYY_MM_DD);
                Date verifiable = DateHelper.parse(weather.getForecastDate(), DateFormat.YYYY_MM_DD);
                if(current.before(verifiable)) {
                    latestWeather = weather;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return latestWeather;
    }
}
