package com.orlinskas.weatherwidget.date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    private final static String YYYY_MM_DD_HH_00 = "yyyy-MM-dd HH:00";
    private final static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String getCurrentWithHour() {
        SimpleDateFormat commonFormat = new SimpleDateFormat(YYYY_MM_DD_HH_00, Locale.ENGLISH);
        return commonFormat.format(new Date());
    }

    public static String getCurrent() {
        SimpleDateFormat commonFormat = new SimpleDateFormat(YYYY_MM_DD, Locale.ENGLISH);
        return commonFormat.format(new Date());
    }
}
