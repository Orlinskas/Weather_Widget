package com.orlinskas.weatherwidget.specification;

import static com.orlinskas.weatherwidget.data.CountryDatabase.COLUMN_COUNTRY_CODE;
import static com.orlinskas.weatherwidget.data.CountryDatabase.TABLE_COUNTRY;

public class CountrySpecification implements SqlSpecification {
    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT DISTINCT * FROM %1$s GROUP BY %2$s;",
                TABLE_COUNTRY,
                COLUMN_COUNTRY_CODE
        );
    }
}
