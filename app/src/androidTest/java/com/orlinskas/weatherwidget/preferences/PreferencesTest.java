package com.orlinskas.weatherwidget.preferences;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class PreferencesTest {

    private Preferences preferences;

    @Before
    public void setUp() {
        preferences = Preferences.getInstance(InstrumentationRegistry.getTargetContext(), Preferences.APP_TEST);
    }

    @After
    public void tearDown() {
        preferences.saveData("testString", "testRestart");
        preferences.saveData("testInt", 0);
        preferences.saveData("testBoolean", true);
    }

    @Test
    public void getInstance() {
        assertNotNull(preferences);
    }

    @Test
    public void saveDataString() {
        preferences.saveData("testString", "test");
        assertEquals("test", preferences.getData("testString", "default"));
    }

    @Test
    public void saveDataInt() {
        preferences.saveData("testInt", 69);
        assertEquals(69, preferences.getData("testInt", 13));
    }

    @Test
    public void saveDataBoolean() {
        preferences.saveData("testBoolean", true);
        assertTrue(preferences.getData("testBoolean", false));
    }
}