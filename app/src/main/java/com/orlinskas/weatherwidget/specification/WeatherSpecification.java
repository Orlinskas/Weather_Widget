package com.orlinskas.weatherwidget.specification;

import static com.orlinskas.weatherwidget.data.WeatherDatabase.TABLE_WEATHER;

public class WeatherSpecification implements SqlSpecification {
    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT DISTINCT * FROM %1$s;",
                TABLE_WEATHER
        );
    }
}
