package com.orlinskas.weatherwidget.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.orlinskas.weatherwidget.City;

import java.util.ArrayList;

public class CitiesDatabaseAdapter {
    private CitiesDatabase citiesDatabase;
    private SQLiteDatabase database;
    private static final String DATABASE_NAME = "Cities.db";
    private static String DATABASE_PATH;
    private static final String TABLE_CITY = "CitiesTable";
    private Context context;

    public CitiesDatabaseAdapter(Context context) {
        citiesDatabase = new CitiesDatabase(context);
        DATABASE_PATH = context.getFilesDir().getPath() + DATABASE_NAME;
        this.context = context;
    }

    public void createDatabase() {
        CitiesDatabase citiesDatabase = new CitiesDatabase(context);
        citiesDatabase.create_db();
    }

    public void openWithTransaction(){
        database = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
        database.beginTransaction();
    }

    public void closeWithTransaction(){
        database.setTransactionSuccessful();
        database.endTransaction();
        citiesDatabase.close();
    }

    public void insert(City city) {
        ContentValues cv = new ContentValues();
        cv.put(CitiesDatabase.COLUMN_CITY_ID, city.getId());
        cv.put(CitiesDatabase.COLUMN_CITY_NAME, city.getName());
        cv.put(CitiesDatabase.COLUMN_COUNTRY_CODE, city.getCountryCode());
        cv.put(CitiesDatabase.COLUMN_COORD_LON, city.getCoordLon());
        cv.put(CitiesDatabase.COLUMN_COORD_LAT, city.getCoordLat());
        database.insert(TABLE_CITY, null, cv);
    }

    public ArrayList<City> getCities() {
        Cursor cursor = getAllEntries();
        ArrayList<City> cities = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int cityID = cursor.getInt(cursor.getColumnIndex(CitiesDatabase.COLUMN_CITY_ID));
                String cityName = cursor.getString(cursor.getColumnIndex(CitiesDatabase.COLUMN_CITY_NAME));
                String countryCode = cursor.getString(cursor.getColumnIndex(CitiesDatabase.COLUMN_COUNTRY_CODE));
                double coordLon = cursor.getDouble(cursor.getColumnIndex(CitiesDatabase.COLUMN_COORD_LON));
                double coordLat = cursor.getDouble(cursor.getColumnIndex(CitiesDatabase.COLUMN_COORD_LAT));

                cities.add(new City(cityID, cityName, countryCode, coordLon, coordLat));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return cities;
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {CitiesDatabase.COLUMN_CITY_ID, CitiesDatabase.COLUMN_CITY_NAME,
                CitiesDatabase.COLUMN_COUNTRY_CODE, CitiesDatabase.COLUMN_COORD_LON,
                CitiesDatabase.COLUMN_COORD_LAT};
        return  database.query(TABLE_CITY, columns, null, null, null, null, null);
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, TABLE_CITY);
    }

    public void removeAll() {
        database.delete(TABLE_CITY, null, null);
    }
}
