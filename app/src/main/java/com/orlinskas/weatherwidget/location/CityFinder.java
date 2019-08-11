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
        CityRepository repository = new CityRepository(context);
        ArrayList<City> cities = repository.query(new CitiesThisLocationSpecification(location));

        City nearCity = null;
        double distanceToNearCity = 99999.0;

        for (City city : cities) {
            double latitudeDistance = Math.pow(location.getLatitude() - city.getCoordLat(), 2.0);
            double longitudeDistance = Math.pow(location.getLongitude() - city.getCoordLon(), 2.0);

            double currentCityDistanceToMe = latitudeDistance + longitudeDistance;

            if (distanceToNearCity > currentCityDistanceToMe) {
                nearCity = city;
                distanceToNearCity = currentCityDistanceToMe;
            }
        }
        return nearCity;
    }
}
