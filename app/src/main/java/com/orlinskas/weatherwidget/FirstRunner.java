package com.orlinskas.weatherwidget;

import android.content.Context;

import com.orlinskas.weatherwidget.data.CityDatabaseAdapter;
import com.orlinskas.weatherwidget.data.CountryDatabaseAdapter;

public class FirstRunner {
    private Context context;

    public FirstRunner(Context context) {
        this.context = context;
    }

    public void doFirstRun() {
        createCityDatabase();
        createCountryDatabase();
    }

    private void createCountryDatabase() {
        CountryDatabaseAdapter countryDatabaseAdapter = new CountryDatabaseAdapter(context);
        countryDatabaseAdapter.createDatabase();
    }

    private void createCityDatabase() {
        CityDatabaseAdapter cityDatabaseAdapter = new CityDatabaseAdapter(context);
        cityDatabaseAdapter.createDatabase();
    }
}
