package com.orlinskas.weatherwidget.location;

import android.content.Context;
import android.location.Location;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.City;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
public class CityFinderTest {
    private Location locationKharkiv;
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        locationKharkiv = mock(Location.class);
        when(locationKharkiv.getLatitude()).thenReturn(50.0);
        when(locationKharkiv.getLongitude()).thenReturn(36.25);
    }

    @Test
    public void find() {
        City kharkov = new CityFinder(context, locationKharkiv).find();
        assertEquals(kharkov.getName(),"Kharkiv");
    }
}