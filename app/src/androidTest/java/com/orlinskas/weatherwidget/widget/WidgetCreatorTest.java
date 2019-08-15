package com.orlinskas.weatherwidget.widget;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.City;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
public class WidgetCreatorTest {
    private Context context;
    private City city;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        city = mock(City.class);
        when(city.getId()).thenReturn(1234);
        when(city.getName()).thenReturn("Kharkiv");
        when(city.getCountryCode()).thenReturn("UA");
        when(city.getCoordLon()).thenReturn(50.0);
        when(city.getCoordLat()).thenReturn(36.6);
    }

    @Test
    public void create() {
        WidgetCreator creator = new WidgetCreator();
        Widget widget = creator.create(city);
        assertSame(widget.getCity(), city);
        assertNotNull(widget.getRequest());
    }
}