package com.orlinskas.weatherwidget.request;

import com.orlinskas.weatherwidget.City;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestBuilderTest {
    private City city;
    private Request request;

    @Before
    public void setUp() {
        city = new City(1, "test","AA",1.0,1.0);
        RequestBuilder requestBuilder = new RequestBuilder();
        request = requestBuilder.build(RequestBuilder.OPEN_WEATHER, city);
    }

    @Test
    public void build() {
        assertEquals(request.getCity(), city);
        assertEquals(request.getApiKey(), "a39b0e16bbd652220c6c82560e6814a6");
        assertEquals(request.getUnitsType(), "metric");
    }
}