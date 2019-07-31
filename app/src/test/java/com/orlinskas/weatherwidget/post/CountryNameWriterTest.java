package com.orlinskas.weatherwidget.post;

import org.junit.Test;

import static org.junit.Assert.*;

public class CountryNameWriterTest {

    @Test
    public void findNameWith() {
        CountryNameWriter countryNameWriter = new CountryNameWriter();
        assertEquals("Ukraine", countryNameWriter.findNameWith("UA"));
    }
}