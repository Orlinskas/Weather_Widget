package com.orlinskas.weatherwidget.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class RandomTest {

    @Test
    public void getID() {
        for(int i = 0; i < 100; i++) {
            int checkValue = Random.getID();
            assertTrue(checkValue <= 9999 & checkValue >= 1000);
        }
    }
}