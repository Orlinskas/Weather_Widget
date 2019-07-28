package com.orlinskas.weatherwidget.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static Preferences preferences;
    private SharedPreferences sharedPreferences;
    private static final String SETTINGS = "settings";
    private static final String JSON_LINE = "jsonLine";
    private static final String APP_STATUS = "appStatus";

    public static Preferences getInstance(Context context, String preferencesName) {
        if (preferences == null) {
            preferences = new Preferences(context, preferencesName);
        }
        return preferences;
    }

    private Preferences(Context context, String preferencesName) {
        sharedPreferences = context.getSharedPreferences(preferencesName,Context.MODE_PRIVATE);
    }

    public void saveData(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public String getData(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }
}