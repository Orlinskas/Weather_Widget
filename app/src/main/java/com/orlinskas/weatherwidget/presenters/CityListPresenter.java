package com.orlinskas.weatherwidget.presenters;

import android.content.Context;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.Country;
import com.orlinskas.weatherwidget.repository.CityRepository;
import com.orlinskas.weatherwidget.specification.CitySpecification;

import java.util.ArrayList;

public class CityListPresenter {
    private Context context;

    public CityListPresenter(Context context) {
        this.context = context;
    }

    public ArrayList<City> present(Country country) {
        ArrayList<City> requiredCountryCities = new ArrayList<>();
        CityRepository repository = new CityRepository(context);
        ArrayList<City> allCities = repository.query(new CitySpecification());

        for(City city : allCities) {
            if (city.getCountryCode() != null && city.getCountryCode().equals(country.getCode())) {
                requiredCountryCities.add(city);
            }
        }

        return requiredCountryCities;
    }
}
