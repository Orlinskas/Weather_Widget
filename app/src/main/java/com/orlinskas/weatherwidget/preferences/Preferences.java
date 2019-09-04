package com.orlinskas.weatherwidget.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class Preferences {
    private static Preferences preferences;
    private SharedPreferences sharedPreferences;
    public static final String SETTINGS = "settings";
    public static final String JSON_LINE = "jsonLine";
    public static final String APP_STATUS = "appStatus";
    public static final String APP_TEST = "test";
    public static final String WIDGET_UPDATE_DATES = "dates";
    public static final String WIDGET_DAY_NUMBER = "day";
    public static final String WIDGET_ID_DEPENDENCE = "dependence";
    public static final String UPDATES_COUNT = "updates";

    public static Preferences getInstance(Context context, String preferencesName) {
        if (preferences == null) {
            preferences = new Preferences(context, preferencesName);
        }
        return preferences;
    }

    private Preferences(Context context, String preferencesName) {
        sharedPreferences = context.getSharedPreferences(preferencesName,Context.MODE_PRIVATE);
    }

    public void saveData(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public String getData(String key, String defValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public void saveData(String key, int value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }

    public int getData(String key, int defValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, defValue);
        }
        return 0;
    }

    public void saveData(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    public boolean getData(String key, boolean defValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, defValue);
        }
        return false;
    }
}