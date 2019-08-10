package com.orlinskas.weatherwidget.specification;

import com.orlinskas.weatherwidget.Country;

import static com.orlinskas.weatherwidget.data.CityDatabase.TABLE_CITY;
import static com.orlinskas.weatherwidget.data.CityDatabase.COLUMN_COUNTRY_CODE;
import static com.orlinskas.weatherwidget.data.CityDatabase.COLUMN_CITY_NAME;

public class CitiesThisCountrySpecification implements SqlSpecification {
    private Country country;

    public CitiesThisCountrySpecification(Country country) {
        this.country = country;
    }

    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM %1$s WHERE %2$s LIKE '%3$s' GROUP BY %4$s;",
                TABLE_CITY,
                COLUMN_COUNTRY_CODE,
                country.getCode(),
                COLUMN_CITY_NAME
        );
    }
}
