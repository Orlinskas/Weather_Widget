package com.orlinskas.weatherwidget.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.orlinskas.weatherwidget.Country;
import com.orlinskas.weatherwidget.data.CountryDatabaseAdapter;
import com.orlinskas.weatherwidget.specification.SqlSpecification;

import java.util.ArrayList;

import static com.orlinskas.weatherwidget.data.CountryDatabase.TABLE_COUNTRY;
import static com.orlinskas.weatherwidget.data.CountryDatabase.COLUMN_COUNTRY_CODE;
import static com.orlinskas.weatherwidget.data.CountryDatabase.COLUMN_COUNTRY_NAME;


public class CountryRepository implements Repository<Country> {
    private SQLiteDatabase database;

    public CountryRepository(Context context) {
        CountryDatabaseAdapter countryDatabaseAdapter = new CountryDatabaseAdapter(context);
        database = countryDatabaseAdapter.getDatabase();
    }

    @Override
    public void add(Country object) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_COUNTRY_CODE, object.getCode());
            cv.put(COLUMN_COUNTRY_NAME, object.getName());

            database.insert(TABLE_COUNTRY, null, cv);
    }

    @Override
    public void update(Country object) {
    }

    @Override
    public void remote(Country object) {
            String sql = String.format(
                    "DELETE FROM %1$s WHERE %2$s = '%3$s';",
                    TABLE_COUNTRY,
                    COLUMN_COUNTRY_CODE,
                    object.getCode()
            );
            database.execSQL(sql);
    }

    @Override
    public ArrayList<Country> query(SqlSpecification sqlSpecification) {
        final ArrayList<Country> countries = new ArrayList<>();
        database.beginTransaction();

        try {
            final Cursor cursor = database.rawQuery(sqlSpecification.toSqlQuery(), new String[]{});

            if (cursor.moveToFirst()) {
                do {
                    String code = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_CODE));
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_NAME));

                    if (name != null) {
                        countries.add(new Country(code, name));
                    } else {
                        countries.add(new Country(code));
                    }
                }while (cursor.moveToNext());
            }
            cursor.close();
            database.setTransactionSuccessful();

        } finally {
            database.endTransaction();
        }
        return countries;
    }
}
