package com.orlinskas.weatherwidget.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CountryDatabaseAdapterTest {

    @Test
    public void getDatabase() {
        CountryDatabaseAdapter countryDatabaseAdapter =
                new CountryDatabaseAdapter(InstrumentationRegistry.getTargetContext());

        assertNotNull(countryDatabaseAdapter.getDatabase());
    }
}