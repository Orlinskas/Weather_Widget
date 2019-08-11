package com.orlinskas.weatherwidget.request;

import com.orlinskas.weatherwidget.City;

import java.io.Serializable;

public class Request implements Serializable {
    private String date;
    private City city;
    private String source;
    private String forecastType;
    private String unitsType;
    private String apiKey;

    public Request(String date, City city, String source, String forecastType, String unitsType, String apiKey) {
        this.date = date;
        this.city = city;
        this.source = source;
        this.forecastType = forecastType;
        this.unitsType = unitsType;
        this.apiKey = apiKey;
    }

    public Request() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getForecastType() {
        return forecastType;
    }

    public void setForecastType(String forecastType) {
        this.forecastType = forecastType;
    }

    public String getUnitsType() {
        return unitsType;
    }

    public void setUnitsType(String unitsType) {
        this.unitsType = unitsType;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
