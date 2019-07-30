package com.orlinskas.weatherwidget.specification;

import static com.orlinskas.weatherwidget.data.CityDatabase.TABLE_CITY;
import static com.orlinskas.weatherwidget.data.CityDatabase.COLUMN_COUNTRY_CODE;

public class CitySpecification implements SqlSpecification {
    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT DISTINCT * FROM %1$s GROUP BY %2$s;",
                TABLE_CITY,
                COLUMN_COUNTRY_CODE
        );
    }
}
