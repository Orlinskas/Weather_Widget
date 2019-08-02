package com.orlinskas.weatherwidget.date;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateHelperTest {

    @Test
    public void getCurrentWithHour() {
        String date = DateHelper.getCurrentWithHour();
        assertEquals(date,16, date.length());
    }

    @Test
    public void getCurrent() {
        String date = DateHelper.getCurrent();
        assertEquals(date,10, date.length());
    }
}