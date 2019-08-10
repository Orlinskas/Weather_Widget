package com.orlinskas.weatherwidget.specification;

import static com.orlinskas.weatherwidget.data.CityDatabase.TABLE_CITY;
import static com.orlinskas.weatherwidget.data.CityDatabase.COLUMN_CITY_NAME;

public class CitiesSpecification implements SqlSpecification {
    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM %1$s GROUP BY %2$s;",
                TABLE_CITY,
                COLUMN_CITY_NAME
        );
    }
}
