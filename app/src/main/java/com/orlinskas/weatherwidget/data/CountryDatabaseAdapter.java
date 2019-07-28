package com.orlinskas.weatherwidget.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.orlinskas.weatherwidget.Country;

import java.util.ArrayList;

public class CountryDatabaseAdapter {
    private CountryDatabase countryDatabase;
    private SQLiteDatabase database;
    private static final String DATABASE_NAME = "Country.db";
    private static String DATABASE_PATH;
    private static final String TABLE_COUNTRY = "CountriesTable";
    private Context context;

    public CountryDatabaseAdapter(Context context) {
        countryDatabase = new CountryDatabase(context);
        DATABASE_PATH = context.getFilesDir().getPath() + DATABASE_NAME;
        this.context = context;
    }

    public void createDatabase() {
        CountryDatabase countryDatabase = new CountryDatabase(context);
        countryDatabase.create_db();
    }

    public void openWithTransaction(){
        database = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
        database.beginTransaction();
    }

    public void closeWithTransaction(){
        database.setTransactionSuccessful();
        database.endTransaction();
        countryDatabase.close();
    }

    public void insert(Country country) {
        ContentValues cv = new ContentValues();
        cv.put(CountryDatabase.COLUMN_COUNTRY_CODE, country.getCode());
        cv.put(CountryDatabase.COLUMN_COUNTRY_NAME, country.getName());

        database.insert(TABLE_COUNTRY, null, cv);
    }

    public ArrayList<Country> getCountries() {
        Cursor cursor = getAllEntries();
        ArrayList<Country> countries = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String code = cursor.getString(cursor.getColumnIndex(CountryDatabase.COLUMN_COUNTRY_CODE));
                String name = cursor.getString(cursor.getColumnIndex(CountryDatabase.COLUMN_COUNTRY_NAME));

                if (name != null) {
                    countries.add(new Country(code, name));
                } else {
                    countries.add(new Country(code));
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return countries;
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {CountryDatabase.COLUMN_COUNTRY_CODE, CountryDatabase.COLUMN_COUNTRY_NAME};
        return  database.query(TABLE_COUNTRY, columns, null, null, null, null, null);
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, TABLE_COUNTRY);
    }

    public void removeAll() {
        database.delete(TABLE_COUNTRY, null, null);
    }
}
