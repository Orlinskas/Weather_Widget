package com.orlinskas.weatherwidget.request;

import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.City;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class RequestURLSenderTest {
    private URL url;

    @Before
    public void setUp() {
        City city = new City(706483, "test","AA",1.0,1.0);
        RequestBuilder builder = new RequestBuilder();
        Request request = builder.build(city);
        RequestURLGenerator urlGenerator = new RequestURLGenerator();
        url = urlGenerator.generate(request);
    }

    @Test
    public void send() {
        RequestURLSender urlSender = new RequestURLSender();
        String response = urlSender.send(url);
        assertNotNull(response);
    }
}