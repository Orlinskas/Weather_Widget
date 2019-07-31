package com.orlinskas.weatherwidget;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicationContextTest {

    @Test
    public void getAppContext() {
        assertNotNull(ApplicationContext.getAppContext());
    }
}