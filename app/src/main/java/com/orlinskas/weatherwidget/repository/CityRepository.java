package com.orlinskas.weatherwidget.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.data.CityDatabaseAdapter;
import com.orlinskas.weatherwidget.specification.SqlSpecification;

import java.util.ArrayList;

import static com.orlinskas.weatherwidget.data.CityDatabase.COLUMN_CITY_ID;
import static com.orlinskas.weatherwidget.data.CityDatabase.COLUMN_CITY_NAME;
import static com.orlinskas.weatherwidget.data.CityDatabase.COLUMN_COORD_LAT;
import static com.orlinskas.weatherwidget.data.CityDatabase.COLUMN_COORD_LON;
import static com.orlinskas.weatherwidget.data.CityDatabase.COLUMN_COUNTRY_CODE;
import static com.orlinskas.weatherwidget.data.CityDatabase.TABLE_CITY;

public class CityRepository implements Repository<City> {
    private SQLiteDatabase database;

    public CityRepository(Context context) {
        CityDatabaseAdapter cityDatabaseAdapter = new CityDatabaseAdapter(context);
        database = cityDatabaseAdapter.getDatabase();
    }

    @Override
    public void add(City object) {
        database.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_CITY_ID, object.getId());
            cv.put(COLUMN_CITY_NAME, object.getName());
            cv.put(COLUMN_COUNTRY_CODE, object.getCountryCode());
            cv.put(COLUMN_COORD_LON, object.getCoordLon());
            cv.put(COLUMN_COORD_LAT, object.getCoordLat());
            database.insert(TABLE_CITY, null, cv);
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            database.close();
        }
    }

    @Override
    public void update(City object) {

    }

    @Override
    public void remove(City object) {

    }

    @Override
    public ArrayList<City> query(SqlSpecification sqlSpecification) {
        final ArrayList<City> cities = new ArrayList<>();
        database.beginTransaction();

        try {
            final Cursor cursor = database.rawQuery(sqlSpecification.toSqlQuery(),new String[]{});

            if (cursor.moveToFirst()) {
                do {
                    int cityID = cursor.getInt(cursor.getColumnIndex(COLUMN_CITY_ID));
                    String cityName = cursor.getString(cursor.getColumnIndex(COLUMN_CITY_NAME));
                    String countryCode = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_CODE));
                    double coordLon = cursor.getDouble(cursor.getColumnIndex(COLUMN_COORD_LON));
                    double coordLat = cursor.getDouble(cursor.getColumnIndex(COLUMN_COORD_LAT));

                    cities.add(new City(cityID, cityName, countryCode, coordLon, coordLat));
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            database.setTransactionSuccessful();

        } finally {
            database.endTransaction();
            database.close();
        }
        return cities;
    }

}
