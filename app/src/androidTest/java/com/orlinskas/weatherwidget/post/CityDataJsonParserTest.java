package com.orlinskas.weatherwidget.post;

import android.support.test.InstrumentationRegistry;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.data.CitiesDatabase;
import com.orlinskas.weatherwidget.data.CitiesDatabaseAdapter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CityDataJsonParserTest {
    private ArrayList<City> cities = new ArrayList<>();
    private CitiesDatabaseAdapter adapter;

    @Before
    public void setUp() {
        adapter = new CitiesDatabaseAdapter(InstrumentationRegistry.getTargetContext());
        adapter.openWithTransaction();
        adapter.removeAll(CitiesDatabase.TABLE_CITY_TEST);
        adapter.closeWithTransaction();

        CityDataJsonParser cityDataJsonParser = new CityDataJsonParser();
        cityDataJsonParser.parseJSONFile();
    }

    @Test
    public void generate() {
        adapter.openWithTransaction();
        cities = adapter.getCities(CitiesDatabase.TABLE_CITY_TEST);
        Assert.assertTrue(cities.size() > 0);
    }

    @After
    public void tearDown() {
        adapter.closeWithTransaction();
    }
}