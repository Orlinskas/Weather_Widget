package com.orlinskas.weatherwidget.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class WeatherDatabaseAdapter {
    private static String DATABASE_PATH;

    public WeatherDatabaseAdapter(Context context) {
        WeatherDatabase weatherDatabase = new WeatherDatabase(context);
        DATABASE_PATH = context.getFilesDir().getPath() + WeatherDatabase.DATABASE_NAME;
    }

    public SQLiteDatabase getDatabase(){
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}