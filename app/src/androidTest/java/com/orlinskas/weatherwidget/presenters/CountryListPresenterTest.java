package com.orlinskas.weatherwidget.presenters;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.Country;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CountryListPresenterTest {

    @Test
    public void present() {
        CountryListPresenter presenter = new CountryListPresenter(InstrumentationRegistry.getTargetContext());
        ArrayList<Country> countries = presenter.present();
        assertEquals(196, countries.size());
    }
}