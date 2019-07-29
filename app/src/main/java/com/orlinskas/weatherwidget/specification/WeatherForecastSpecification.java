package com.orlinskas.weatherwidget.specification;

import com.orlinskas.weatherwidget.WeatherForecast;

public class WeatherForecastSpecification implements Specification<WeatherForecast>, SqlSpecification {
    @Override
    public boolean specified(WeatherForecast object) {
        return false;
    }

    @Override
    public String toSqlQuery() {
        return null;
    }
}
