package com.orlinskas.weatherwidget.specification;

import android.location.Location;

import static com.orlinskas.weatherwidget.data.CityDatabase.COLUMN_COORD_LAT;
import static com.orlinskas.weatherwidget.data.CityDatabase.TABLE_CITY;

public class CitiesThisLocationSpecification implements SqlSpecification {
    private Location location;
    private final static double MAX_COORDINATES_DEVIATION = 2.0;

    public CitiesThisLocationSpecification(Location location) {
        this.location = location;
    }

    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM %1$s WHERE %2$s BETWEEN %3$s AND %4$s;",
                TABLE_CITY,
                COLUMN_COORD_LAT,
                location.getLatitude() - MAX_COORDINATES_DEVIATION,
                location.getLatitude() + MAX_COORDINATES_DEVIATION
        );
    }
}
