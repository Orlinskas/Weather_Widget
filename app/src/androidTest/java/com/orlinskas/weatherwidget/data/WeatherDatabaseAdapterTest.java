package com.orlinskas.weatherwidget.data;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.WeatherForecast;
import com.orlinskas.weatherwidget.data.WeatherDatabase;
import com.orlinskas.weatherwidget.data.WeatherDatabaseAdapter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class WeatherDatabaseAdapterTest {
    private WeatherDatabaseAdapter weatherDatabaseAdapter;
    private WeatherForecast weatherForecast;
    private String testTableName;

    @Before
    public void setUp() {
        testTableName = WeatherDatabase.TABLE_WEATHER_TEST;
        weatherForecast = createObjectWeatherForecast();
        Context context = InstrumentationRegistry.getTargetContext();
        weatherDatabaseAdapter = new WeatherDatabaseAdapter(context);
        weatherDatabaseAdapter.openWithTransaction();
    }

    private WeatherForecast createObjectWeatherForecast() {
        return new WeatherForecast(1,"name", "ua",
                "2017-01-30 15:00:00", "2027-01-30 18:00:00", 15,
                1025, 75, 800, "Clear", "clear sky",
                "02n", 8, 4.77, 0, 0);
    }

    @After
    public void tearDown() {
        weatherDatabaseAdapter.closeWithTransaction();
    }

    @Test
    public void insert() {
        weatherDatabaseAdapter.insert(weatherForecast, testTableName);
        Assert.assertTrue(isInsertDone());
    }

    private boolean isInsertDone() {
        ArrayList<WeatherForecast> weathers = weatherDatabaseAdapter.getWeathers(testTableName);
        WeatherForecast insertedWeatherForecast = weathers.get(weathers.size() - 1);
        return weatherForecast.equals(insertedWeatherForecast);
    }

    @Test
    public void getWeathers() {
        ArrayList<WeatherForecast> weathers = weatherDatabaseAdapter.getWeathers(testTableName);
        Assert.assertNotNull(weathers);
    }

    @Test
    public void getCount() {
        weatherDatabaseAdapter.insert(weatherForecast, testTableName);
        Assert.assertTrue(weatherDatabaseAdapter.getCount(testTableName) > 0);
    }

    @Test
    public void removeAll() {
        weatherDatabaseAdapter.removeAll(testTableName);
        Assert.assertEquals(0, weatherDatabaseAdapter.getCount(testTableName));
    }
}