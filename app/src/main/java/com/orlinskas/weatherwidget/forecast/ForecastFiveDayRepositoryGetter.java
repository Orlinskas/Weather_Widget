package com.orlinskas.weatherwidget.forecast;

import android.content.Context;

import com.orlinskas.weatherwidget.date.DateFormat;
import com.orlinskas.weatherwidget.date.DateHelper;
import com.orlinskas.weatherwidget.repository.WeatherRepository;
import com.orlinskas.weatherwidget.specification.WeatherWidgetSpecification;
import com.orlinskas.weatherwidget.widget.Widget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ForecastFiveDayRepositoryGetter {
    private Widget widget;
    private Context context;

    public ForecastFiveDayRepositoryGetter(Widget widget, Context context) {
        this.widget = widget;
        this.context = context;
    }

    public ForecastFiveDay get() {
        WeatherRepository weatherRepository = new WeatherRepository(context);
        ArrayList<Weather> weathers = weatherRepository.query(new WeatherWidgetSpecification(widget));

        return process(weathers);

    }

    private ForecastFiveDay process(ArrayList<Weather> weathers) throws ParseException {
        ForecastFiveDay forecastFiveDay = new ForecastFiveDay();
        ForecastOneDay[] forecastOneDays = forecastFiveDay.getDays();

        Date current = DateHelper.getCurrentDate(DateFormat.YYYY_MM_DD);
        Date currentPlusDay = current.setHours(current.getHours() + 24);

        for(Weather weather : weathers) {
            if(getDate(weather).equals(current)) {
                forecastOneDays[0].getDayWeathers().add(weather);
            }
            if(getDate(weather).equals(){

            }
        }
    }

    private Date getDate(Weather weather) throws ParseException {
        String textDate = weather.getForecastDate().substring(0,10);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormat.YYYY_MM_DD, Locale.ENGLISH);
        return dateFormat.parse(textDate);
    }
}
