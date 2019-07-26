package com.orlinskas.weatherwidget.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CitiesDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Cities.db";

    public static String TABLE_CITY = "CitiesTable";
    public static String TABLE_CITY_TEST = "CitiesTableTest";

    private static final String COLUMN_ID = "_id";
    static final String COLUMN_CITY_ID = "cityID";
    static final String COLUMN_CITY_NAME = "cityName";
    static final String COLUMN_COUNTRY_CODE = "countryCode";
    static final String COLUMN_COORD_LON = "lon";
    static final String COLUMN_COORD_LAT = "lat";


    CitiesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE_CITIES = "CREATE TABLE " + TABLE_CITY + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CITY_ID + " INTEGER NOT NULL , "
                + COLUMN_CITY_NAME + " TEXT , "
                + COLUMN_COUNTRY_CODE + " TEXT , "
                + COLUMN_COORD_LON + " REAL NOT NULL DEFAULT 0.0 , "
                + COLUMN_COORD_LAT + " REAL NOT NULL DEFAULT 0.0 );";

        String SQL_CREATE_TABLE_CITIES_TEST = "CREATE TABLE " + TABLE_CITY_TEST + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CITY_ID + " INTEGER NOT NULL , "
                + COLUMN_CITY_NAME + " TEXT , "
                + COLUMN_COUNTRY_CODE + " TEXT , "
                + COLUMN_COORD_LON + " REAL NOT NULL DEFAULT 0.0 , "
                + COLUMN_COORD_LAT + " REAL NOT NULL DEFAULT 0.0 );";

        db.execSQL(SQL_CREATE_TABLE_CITIES);
        db.execSQL(SQL_CREATE_TABLE_CITIES_TEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY_TEST);
        onCreate(db);
    }
}
