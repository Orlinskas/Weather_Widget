package com.orlinskas.weatherwidget.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CityDatabaseAdapter {
    private CityDatabase cityDatabase;
    private static final String DATABASE_NAME = "Cities.db";
    private static String DATABASE_PATH;

    public CityDatabaseAdapter(Context context) {
        cityDatabase = new CityDatabase(context);
        DATABASE_PATH = context.getFilesDir().getPath() + DATABASE_NAME;
    }

    public void createDatabase() {
        cityDatabase.create_db();
    }

    public SQLiteDatabase getDatabase(){
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
