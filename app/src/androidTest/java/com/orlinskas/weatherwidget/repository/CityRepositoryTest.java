package com.orlinskas.weatherwidget.repository;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.specification.CitySpecification;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CityRepositoryTest {
    private CityRepository repository;
    private City city;
    private ArrayList<City> cities;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        city = mock(City.class);
        when(city.getId()).thenReturn(101010101);
    }

    @Test
    public void add() {
        repository = new CityRepository(InstrumentationRegistry.getTargetContext());
        repository.add(city);
        repository = new CityRepository(InstrumentationRegistry.getTargetContext());
        cities = repository.query(new CitySpecification());
        assertTrue(cities.contains(city));
        repository = new CityRepository(InstrumentationRegistry.getTargetContext());
        repository.remote(city);
    }

    @Test
    public void query() {
        repository = new CityRepository(InstrumentationRegistry.getContext());
        cities = repository.query(new CitySpecification());
        assertTrue(cities.size() > 100000);
    }
}