package com.orlinskas.weatherwidget.json;

import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

@RunWith(AndroidJUnit4.class)
public class JSONInputSteamCreatorTest {
    private InputStream inputStream;

    @Before
    public void setUp() {
        JSONInputSteamCreator jsonInputSteamCreator = new JSONInputSteamCreator();
        inputStream = jsonInputSteamCreator.create(R.raw.city_list_min_test);
    }

    @After
    public void tearDown() throws Exception {
        inputStream.close();
    }

    @Test
    public void create() {
        Assert.assertNotNull(inputStream);
    }
}