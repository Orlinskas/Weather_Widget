package com.orlinskas.weatherwidget.location;

import android.content.Context;
import android.location.Location;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.repository.CityRepository;
import com.orlinskas.weatherwidget.specification.CitiesSpecification;

import java.util.ArrayList;

public class CityFinder {

    private Context context;

    public CityFinder(Context context) {
        this.context = context;
    }

    public City find(Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();

        return findCity(lat, lon);
    }

    private City findCity(double lat, double lon) {
        CityRepository repository = new CityRepository(context);
        ArrayList<City> cities = repository.query(new CitiesSpecification());

        return null;


    }
}
