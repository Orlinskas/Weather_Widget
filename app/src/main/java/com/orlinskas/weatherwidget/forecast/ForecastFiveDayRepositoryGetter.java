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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ForecastFiveDayRepositoryGetter {
    private Widget widget;
    private Context context;

    public ForecastFiveDayRepositoryGetter(Widget widget, Context context) {
        this.widget = widget;
        this.context = context;
    }

    public ForecastFiveDay get() throws ParseException {
        WeatherRepository weatherRepository = new WeatherRepository(context);
        ArrayList<Weather> weathers = weatherRepository.query(new WeatherWidgetSpecification(widget));

        return processFiveDays(weathers);
    }

    private ForecastFiveDay processFiveDays(ArrayList<Weather> weathers) throws ParseException {
        ForecastFiveDay forecastFiveDay = new ForecastFiveDay();
        ForecastOneDay[] forecastOneDays = forecastFiveDay.getDays();

        Date currentDate = DateHelper.getCurrentDate(DateFormat.YYYY_MM_DD);

        forecastOneDays[0] = processDay(weathers, currentDate);
        forecastOneDays[1] = processDay(weathers, plusDays(currentDate, 1));
        forecastOneDays[2] = processDay(weathers, plusDays(currentDate, 2));
        forecastOneDays[3] = processDay(weathers, plusDays(currentDate, 3));
        forecastOneDays[4] = processDay(weathers, plusDays(currentDate, 4));

        forecastFiveDay.setDays(forecastOneDays);

        return forecastFiveDay;
    }

    private ForecastOneDay processDay(ArrayList<Weather> weathers, Date date) throws ParseException {
        ArrayList<Weather> dayWeathers = new ArrayList<>();
        for (Weather weather : weathers) {
            if(getDate(weather).equals(date)){
                dayWeathers.add(weather);
            }
        }
        ForecastOneDay forecastOneDay = new ForecastOneDay(date);
        forecastOneDay.setDayWeathers(dayWeathers);
        return forecastOneDay;
    }

    private Date getDate(Weather weather) throws ParseException {
        String textDate = weather.getForecastDate().substring(0,10);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormat.YYYY_MM_DD, Locale.ENGLISH);
        return dateFormat.parse(textDate);
    }

    private Date plusDays(Date date, int countDays) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, countDays);
        return c.getTime();
    }
}
