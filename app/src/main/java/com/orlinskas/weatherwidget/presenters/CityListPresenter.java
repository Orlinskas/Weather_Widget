package com.orlinskas.weatherwidget.presenters;

import android.content.Context;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.Country;
import com.orlinskas.weatherwidget.repository.CityRepository;
import com.orlinskas.weatherwidget.specification.CitiesThisCountrySpecification;

import java.util.ArrayList;

public class CityListPresenter {
    private Context context;

    public CityListPresenter(Context context) {
        this.context = context;
    }

    public ArrayList<City> present(Country country) {
        CityRepository repository = new CityRepository(context);
        return repository.query(new CitiesThisCountrySpecification(country));
    }
}
