package com.orlinskas.weatherwidget;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ApplicationContextTest {

    @Test
    public void getAppContext() {
        assertNotNull(ApplicationContext.getAppContext());
    }
}