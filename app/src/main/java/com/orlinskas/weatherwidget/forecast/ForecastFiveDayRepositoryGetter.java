package com.orlinskas.weatherwidget.forecast;

import android.content.Context;

import com.orlinskas.weatherwidget.date.DateFormat;
import com.orlinskas.weatherwidget.date.DateHelper;
import com.orlinskas.weatherwidget.repository.WeatherRepository;
import com.orlinskas.weatherwidget.specification.WeatherTimesSpecification;
import com.orlinskas.weatherwidget.widget.Widget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ForecastFiveDayRepositoryGetter {
    private final String TIME_03_00 = " 03:00";
    private final String TIME_06_00 = " 06:00";
    private final String TIME_09_00 = " 09:00";
    private final String TIME_12_00 = " 12:00";
    private final String TIME_15_00 = " 15:00";
    private final String TIME_18_00 = " 18:00";
    private final String TIME_21_00 = " 21:00";
    private final String TIME_00_00 = " 00:00";

    private Widget widget;
    private Context context;
    private Date currentDate = DateHelper.getCurrentDate(DateFormat.YYYY_MM_DD);
    private String current = DateHelper.parse(currentDate, DateFormat.YYYY_MM_DD);


    public ForecastFiveDayRepositoryGetter(Widget widget, Context context) {
        this.widget = widget;
        this.context = context;
    }

    public ForecastFiveDay process() {
        ForecastFiveDay forecastFiveDay = new ForecastFiveDay();
        ForecastOneDay[] forecastOneDays = forecastFiveDay.getDays();

        forecastOneDays[0] = processDay(current);
        forecastOneDays[1] = processDay(plusDaysTo(current, 1));
        forecastOneDays[2] = processDay(plusDaysTo(current, 2));
        forecastOneDays[3] = processDay(plusDaysTo(current, 3));
        forecastOneDays[4] = processDay(plusDaysTo(current, 4));

        return forecastFiveDay;
    }

    private ForecastOneDay processDay(String date) {
        WeatherRepository repository = new WeatherRepository(context);
        ArrayList<Weather> dayWeathers = new ArrayList<>();

        String[] dates = {date + TIME_03_00, date + TIME_06_00, date + TIME_09_00,
                date + TIME_12_00, date + TIME_15_00, date + TIME_18_00, date + TIME_21_00,
                plusDaysTo(date, 1) + TIME_00_00};

        for(String dateWithHours : dates){
            ArrayList<Weather> weathersOnTime
                    = repository.query(new WeatherTimesSpecification(widget, dateWithHours));
            dayWeathers.add(takeNewest(weathersOnTime));
        }

        ForecastOneDay forecastOneDay = new ForecastOneDay(date);
        forecastOneDay.setDayWeathers(dayWeathers);
        return forecastOneDay;
    }

    private String plusDaysTo(String dateLine, int countDays) {
        Date date = DateHelper.parse(dateLine, DateFormat.YYYY_MM_DD);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, countDays);
        Date dateAfterPlusDay = c.getTime();
        return DateHelper.parse(dateAfterPlusDay, DateFormat.YYYY_MM_DD);
    }

    private Weather takeNewest(ArrayList<Weather> weathersOnTime) {
        return weathersOnTime.get(0);
    }






}
