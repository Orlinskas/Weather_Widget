package com.orlinskas.weatherwidget.data;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.City;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class CitiesDatabaseAdapterTest {
    private CitiesDatabaseAdapter citiesDatabaseAdapter;
    private City city;
    private String testTableName;

    @Before
    public void setUp() {
        testTableName = CitiesDatabase.TABLE_CITY_TEST;
        city = createObjectCity();
        Context context = InstrumentationRegistry.getTargetContext();
        citiesDatabaseAdapter = new CitiesDatabaseAdapter(context);
        citiesDatabaseAdapter.openWithTransaction();
    }

    private City createObjectCity() {
        return new City(13400, "Kiev", "UA", 13.567, 37.567);
    }

    @After
    public void tearDown() {
        citiesDatabaseAdapter.closeWithTransaction();
    }

    @Test
    public void insert() {
        citiesDatabaseAdapter.insert(city, testTableName);
        Assert.assertTrue(isInsertDone());
    }

    private boolean isInsertDone() {
        ArrayList<City> cities = citiesDatabaseAdapter.getCities(testTableName);
        City insertedCity = cities.get(cities.size() - 1);
        return city.equals(insertedCity);
    }

    @Test
    public void getCities() {
        ArrayList<City> cities = citiesDatabaseAdapter.getCities(testTableName);
        Assert.assertNotNull(cities);
    }

    @Test
    public void getCount() {
        citiesDatabaseAdapter.insert(city, testTableName);
        Assert.assertTrue(citiesDatabaseAdapter.getCount(testTableName) > 0);
    }

    @Test
    public void removeAll() {
        citiesDatabaseAdapter.removeAll(testTableName);
        Assert.assertEquals(0, citiesDatabaseAdapter.getCount(testTableName));
    }
}