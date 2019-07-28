package com.orlinskas.weatherwidget.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CitiesDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Cities.db";
    private static String DATABASE_PATH;

    public static String TABLE_CITY = "CitiesTable";
    public static String TABLE_CITY_TEST = "CitiesTableTest";

    private static final String COLUMN_ID = "_id";
    static final String COLUMN_CITY_ID = "cityID";
    static final String COLUMN_CITY_NAME = "cityName";
    static final String COLUMN_COUNTRY_CODE = "countryCode";
    static final String COLUMN_COORD_LON = "lon";
    static final String COLUMN_COORD_LAT = "lat";

    private Context context;


    CitiesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        DATABASE_PATH = context.getFilesDir().getPath() + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    void create_db(){
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DATABASE_PATH);
            if (!file.exists()) {
                this.getReadableDatabase();
                myInput = context.getAssets().open(DATABASE_NAME);
                String outFileName = DATABASE_PATH;

                myOutput = new FileOutputStream(outFileName);

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }
        catch(IOException ex){
            Log.d("CitiesDataBase", ex.getMessage());
        }
    }
}
