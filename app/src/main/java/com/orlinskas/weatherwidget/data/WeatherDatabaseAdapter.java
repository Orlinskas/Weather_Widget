package com.orlinskas.weatherwidget.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.orlinskas.weatherwidget.WeatherForecast;

import java.util.ArrayList;

public class WeatherDatabaseAdapter {
    private WeatherDatabase weatherDatabase;
    private SQLiteDatabase database;

    public WeatherDatabaseAdapter(Context context) {
        weatherDatabase = new WeatherDatabase(context);
    }

    public SQLiteDatabase getDatabase(){
        return database = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}