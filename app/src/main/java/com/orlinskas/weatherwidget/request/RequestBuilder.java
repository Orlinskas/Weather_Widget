package com.orlinskas.weatherwidget.request;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.date.DateHelper;

public class RequestBuilder {
    public static final String OPEN_WEATHER = "OpenWeather";
    private final static String OPENWEATHERMAP_COM = "http://api.openweathermap.org/";
    private final static String OPENWEATHERMAP_FORECAST_5day = "data/2.5/forecast";
    private final static String OPENWEATHERMAP_API_KEY = "a39b0e16bbd652220c6c82560e6814a6";
    private final static String OPENWEATHERMAP_UNITS = "metric";

    private final static String ACCUWEATHER_COM = "https://dataservice.accuweather.com/";
    private final static String ACCUWEATHER_FORECAST_5day = "forecasts/v1/daily/5day/";
    private final static String ACCUWEATHER_API_KEY = "GxWxbGf8Mwh74gEwcAtkq6Pfg9lGAiTf";
    private final static String ACCUWEATHER_KHARKIV_ID = "323903";
    private final static String ACCUWEATHER_MOSKOW_ID = "294021";
    private final static String ACCUWEATHER_LUBLIN_ID = "274231";
    private final static String ACCUWEATHER_VILNIUS_ID = "231459";
    private final static String ACCUWEATHER_CHECK = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/323903?apikey=GxWxbGf8Mwh74gEwcAtkq6Pfg9lGAiTf&details=false&metric=true";
    private final static String OPENWEATHERMAP_CHECK = "http://api.openweathermap.org/data/2.5/forecast?id=706483&APPID=a39b0e16bbd652220c6c82560e6814a6&units=metric";

    private String date;
    private String source;
    private String forecastType;
    private String unitsType;
    private String apiKey;

    public Request build(String source, City city) {
        switch (source) {
            case OPEN_WEATHER:
                return buildOpenWeather(city);
            default:return new Request();
        }
    }

    private Request buildOpenWeather(City city) {
        date = DateHelper.getCurrent();
        source = OPENWEATHERMAP_COM;
        forecastType = OPENWEATHERMAP_FORECAST_5day;
        unitsType = OPENWEATHERMAP_UNITS;
        apiKey = OPENWEATHERMAP_API_KEY;
        return new Request(date, city, source, forecastType, unitsType, apiKey);
    }
}