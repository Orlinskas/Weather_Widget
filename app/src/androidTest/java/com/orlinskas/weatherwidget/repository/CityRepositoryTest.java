package com.orlinskas.weatherwidget.repository;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.specification.CitiesSpecification;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CityRepositoryTest {
    private CityRepository repository = new CityRepository(InstrumentationRegistry.getTargetContext());
    private ArrayList<City> cities;
    private City city;

    @Before
    public void setUp() {
        city = new City(1, "test","AA",1.0,1.0);
        repository = new CityRepository(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void add() {
        repository.add(city);
        cities = repository.query(new CitiesSpecification());
        assertTrue(cities.contains(city));
        repository.remote(city);
    }

    @Test
    public void query() {
        cities = repository.query(new CitiesSpecification());
        assertTrue(cities.size() > 160000);
    }
}