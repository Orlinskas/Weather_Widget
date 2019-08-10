package com.orlinskas.weatherwidget.location;

import android.content.Context;
import android.location.Location;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.repository.CityRepository;
import com.orlinskas.weatherwidget.specification.CitiesThisLocationSpecification;

import java.util.ArrayList;

public class CityFinder {

    private Context context;
    private Location location;

    public CityFinder(Context context, Location location) {
        this.context = context;
        this.location = location;
    }

    public City find() {
        double lat = location.getLatitude();
        double lon = location.getLongitude();

        return findCity(lat, lon);
    }

    private City findCity(double lat, double lon) {
        CityRepository repository = new CityRepository(context);
        ArrayList<City> cities = repository.query(new CitiesThisLocationSpecification(location));

        return cities.get(1);
    }
}
