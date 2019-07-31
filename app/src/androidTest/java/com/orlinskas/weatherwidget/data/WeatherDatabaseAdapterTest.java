package com.orlinskas.weatherwidget.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class WeatherDatabaseAdapterTest {

    @Test
    public void getDatabase() {
        WeatherDatabaseAdapter weatherDatabaseAdapter =
                new WeatherDatabaseAdapter(InstrumentationRegistry.getTargetContext());

        assertNotNull(weatherDatabaseAdapter);
    }
}