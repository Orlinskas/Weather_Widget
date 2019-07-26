package com.orlinskas.weatherwidget.json;

import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;

@RunWith(AndroidJUnit4.class)
public class JSONSteamReaderTest {
    private InputStream inputStream;
    private String json;

    @Before
    public void setUp() {
        JSONInputSteamCreator jsonInputSteamCreator = new JSONInputSteamCreator();
        inputStream = jsonInputSteamCreator.create(R.raw.city_list_min_test);

        JSONSteamReader jsonSteamReader = new JSONSteamReader();
        json = jsonSteamReader.read(inputStream);
    }

    @After
    public void tearDown() throws IOException {
        inputStream.close();
    }

    @Test
    public void read() {
        Assert.assertNotNull(json);
        Assert.assertTrue(json.length() > 0);
    }

    @Test
    public void readCorrectly() {
        Assert.assertTrue(json.contains("Republic of India"));
    }
}