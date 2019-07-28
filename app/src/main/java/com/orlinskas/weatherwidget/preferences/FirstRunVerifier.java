package com.orlinskas.weatherwidget.preferences;

import android.content.Context;

public class FirstRunVerifier {
    private Context context;
    private Preferences preferences;
    private final String FIRST_RUN = "firstRun";

    public FirstRunVerifier(Context context) {
        this.context = context;
    }

    public boolean check() {
        preferences = Preferences.getInstance(context,Preferences.APP_STATUS);
        return preferences.getData(FIRST_RUN, false);
    }

    public void setFirstRun(boolean value) {
        preferences = Preferences.getInstance(context,Preferences.APP_STATUS);
        preferences.saveData(FIRST_RUN, value);
    }
}
