package com.orlinskas.weatherwidget.json;

import com.orlinskas.weatherwidget.ApplicationContext;

import java.io.InputStream;

public class JSONInputSteamCreator {
    public InputStream create(int path) {
        return ApplicationContext.getAppContext().getResources()
                .openRawResource(path);
    }
}
