package com.orlinskas.weatherwidget.repository;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.Country;
import com.orlinskas.weatherwidget.specification.CountrySpecification;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CountryRepositoryTest {
    private CountryRepository repository;
    private Country country = new Country("TEST", "TestCountry");
    private ArrayList<Country> countries;

    @Test
    public void add() {
        repository = new CountryRepository(InstrumentationRegistry.getTargetContext());
        repository.add(country);
        countries = repository.query(new CountrySpecification());
        assertTrue(countries.contains(country));
        repository.remote(country);
    }

    @Test
    public void remote() {
        repository = new CountryRepository(InstrumentationRegistry.getTargetContext());
        int beforeRemote = repository.query(new CountrySpecification()).size();
        repository.add(country);
        repository.remote(country);
        int afterRemote = repository.query(new CountrySpecification()).size();
        assertEquals(beforeRemote, afterRemote);
    }

    @Test
    public void query() {
        repository = new CountryRepository(InstrumentationRegistry.getTargetContext());
        countries = repository.query(new CountrySpecification());
        assertTrue(countries.size() > 1);
    }
}