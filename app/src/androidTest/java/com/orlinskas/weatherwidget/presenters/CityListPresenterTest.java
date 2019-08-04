package com.orlinskas.weatherwidget.presenters;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.Country;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CityListPresenterTest {
    private Context context;
    private Country country;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        country = new Country("UA", "Ukraine");
    }

    @Test
    public void present() {
        CityListPresenter presenter = new CityListPresenter(context);
        ArrayList<City> cities = presenter.present(country);
        assertTrue(cities.size() > 1500);
    }
}