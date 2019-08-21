package com.orlinskas.weatherwidget.forecast;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.widget.Widget;
import com.orlinskas.weatherwidget.widget.WidgetCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class LatestDatesListBuilderTest {
    private LatestDatesListBuilder fromRepository;
    private ArrayList<String> dates;
    private Context context;
    private City city;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        city = mock(City.class);
        when(city.getId()).thenReturn(1234);
        when(city.getName()).thenReturn("Kharkiv");
        when(city.getCountryCode()).thenReturn("UA");
        when(city.getCoordLon()).thenReturn(50.0);
        when(city.getCoordLat()).thenReturn(36.6);
        WidgetCreator creator = new WidgetCreator();
        Widget widget = creator.create(city);
        fromRepository = new LatestDatesListBuilder(widget, context);
    }

    @Test
    public void getUnique() {
        dates = fromRepository.build();
        assertNotNull(dates);
    }
}