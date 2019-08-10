package com.orlinskas.weatherwidget.specification;

import android.location.Location;

import static com.orlinskas.weatherwidget.data.CityDatabase.COLUMN_COORD_LAT;
import static com.orlinskas.weatherwidget.data.CityDatabase.COLUMN_COORD_LON;
import static com.orlinskas.weatherwidget.data.CityDatabase.TABLE_CITY;

public class CitiesThisLocationSpecification implements SqlSpecification {
    private Location location;
    private final double MAX_COORDINATES_DEVIATION = 5.0;

    public CitiesThisLocationSpecification(Location location) {
        this.location = location;
    }

    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM %1$s WHERE %2$s BETWEEN %3$s AND %4$s;",
                TABLE_CITY,
                COLUMN_COORD_LAT,
                50.0 - MAX_COORDINATES_DEVIATION,
                50.0 + MAX_COORDINATES_DEVIATION
        );
    }
}
