package com.orlinskas.weatherwidget.preferences;

import android.content.Context;

import static com.orlinskas.weatherwidget.preferences.Preferences.SETTINGS;

public class FirstRunVerifier {
    private Context context;
    private Preferences preferences;
    private final String FIRST_RUN = "firstRun";

    public FirstRunVerifier(Context context) {
        this.context = context;
    }

    public boolean check() {
        preferences = Preferences.getInstance(context, SETTINGS);
        return preferences.getData(FIRST_RUN, false);
    }

    public void setFirstRun(boolean value) {
        preferences = Preferences.getInstance(context, SETTINGS);
        preferences.saveData(FIRST_RUN, value);
    }
}
