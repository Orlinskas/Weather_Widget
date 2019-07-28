package com.orlinskas.weatherwidget.post;

import com.orlinskas.weatherwidget.ApplicationContext;
import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.data.CitiesDatabase;
import com.orlinskas.weatherwidget.data.CitiesDatabaseAdapter;

import java.util.ArrayList;

public class CityDataRepositoryWriter {
    public void write(ArrayList<City> cities) {
        CitiesDatabaseAdapter adapter = new CitiesDatabaseAdapter(ApplicationContext.getAppContext());
        adapter.openWithTransaction();

        for(City city : cities) {
            adapter.insert(city, CitiesDatabase.TABLE_CITY);
        }

        adapter.closeWithTransaction();
    }

}
