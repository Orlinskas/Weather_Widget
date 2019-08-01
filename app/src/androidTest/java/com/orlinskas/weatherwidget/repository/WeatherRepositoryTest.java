package com.orlinskas.weatherwidget.repository;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.Weather;
import com.orlinskas.weatherwidget.specification.WeatherSpecification;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class WeatherRepositoryTest {
    private WeatherRepository repository;
    private ArrayList<Weather> weathers;
    private Weather weather;

    @Before
    public void setUp() {
        weather = new Weather(1,"test","ZZ","asfg","sag",1,1,1,1,"dg","asga","asdg",123,1.0,1,1);
        repository = new WeatherRepository(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void add() {
        repository.add(weather);
        weathers = repository.query(new WeatherSpecification());
        assertTrue(weathers.contains(weather));
    }
}