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

    public void openWithTransaction(){
        database = weatherDatabase.getWritableDatabase();
        database.beginTransaction();
    }

    public void closeWithTransaction(){
        database.setTransactionSuccessful();
        database.endTransaction();
        weatherDatabase.close();
    }

    public void insert(WeatherForecast weatherForecast, String tableName) {
        ContentValues cv = new ContentValues();
        cv.put(WeatherDatabase.COLUMN_CITY_ID, weatherForecast.getCityID());
        cv.put(WeatherDatabase.COLUMN_CITY_NAME, weatherForecast.getCityName());
        cv.put(WeatherDatabase.COLUMN_COUNTRY_CODE, weatherForecast.getCountryCode());
        cv.put(WeatherDatabase.COLUMN_TIME_OF_DATA_FORECAST, weatherForecast.getTimeOfDataForecast());
        cv.put(WeatherDatabase.COLUMN_FORECAST_DATE, weatherForecast.getForecastDate());
        cv.put(WeatherDatabase.COLUMN_CURRENT_TEMPERATURE, weatherForecast.getCurrentTemperature());
        cv.put(WeatherDatabase.COLUMN_PRESSURE, weatherForecast.getPressure());
        cv.put(WeatherDatabase.COLUMN_HUMIDITY_PERCENT, weatherForecast.getHumidityPercent());
        cv.put(WeatherDatabase.COLUMN_WEATHER_ID, weatherForecast.getWeatherID());
        cv.put(WeatherDatabase.COLUMN_WEATHER_GROUP, weatherForecast.getWeatherGroup());
        cv.put(WeatherDatabase.COLUMN_WEATHER_GROUP_DESCRIPTION, weatherForecast.getWeatherGroupDescription());
        cv.put(WeatherDatabase.COLUMN_WEATHER_ICON_ID, weatherForecast.getWeatherIconID());
        cv.put(WeatherDatabase.COLUMN_CLOUDINESS_PERCENT, weatherForecast.getCloudinessPercent());
        cv.put(WeatherDatabase.COLUMN_WIND_SPEED, weatherForecast.getWindSpeed());
        cv.put(WeatherDatabase.COLUMN_RAIN_VOLUME, weatherForecast.getRainVolume());
        cv.put(WeatherDatabase.COLUMN_SNOW_VOLUME, weatherForecast.getSnowVolume());
        database.insert(tableName, null, cv);
    }

    public ArrayList<WeatherForecast> getWeathers(String tableName) {
        Cursor cursor = getAllEntries(tableName);
        ArrayList<WeatherForecast> weathers = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int cityID = cursor.getInt(cursor.getColumnIndex(WeatherDatabase.COLUMN_CITY_ID));
                String cityName = cursor.getString(cursor.getColumnIndex(WeatherDatabase.COLUMN_CITY_NAME));
                String countryCode = cursor.getString(cursor.getColumnIndex(WeatherDatabase.COLUMN_COUNTRY_CODE));
                String timeOfDataForecast = cursor.getString(cursor.getColumnIndex(WeatherDatabase.COLUMN_TIME_OF_DATA_FORECAST));
                String forecastDate = cursor.getString(cursor.getColumnIndex(WeatherDatabase.COLUMN_FORECAST_DATE));
                int currentTemperature = cursor.getInt(cursor.getColumnIndex(WeatherDatabase.COLUMN_CURRENT_TEMPERATURE));
                int pressure = cursor.getInt(cursor.getColumnIndex(WeatherDatabase.COLUMN_PRESSURE));
                int humidityPercent = cursor.getInt(cursor.getColumnIndex(WeatherDatabase.COLUMN_HUMIDITY_PERCENT));
                int weatherID = cursor.getInt(cursor.getColumnIndex(WeatherDatabase.COLUMN_WEATHER_ID));
                String weatherGroup = cursor.getString(cursor.getColumnIndex(WeatherDatabase.COLUMN_WEATHER_GROUP));
                String weatherGroupDescription = cursor.getString(cursor.getColumnIndex(WeatherDatabase.COLUMN_WEATHER_GROUP_DESCRIPTION));
                String weatherIconID = cursor.getString(cursor.getColumnIndex(WeatherDatabase.COLUMN_WEATHER_ICON_ID));
                int cloudinessPercent = cursor.getInt(cursor.getColumnIndex(WeatherDatabase.COLUMN_CLOUDINESS_PERCENT));
                double windSpeed = cursor.getDouble(cursor.getColumnIndex(WeatherDatabase.COLUMN_WIND_SPEED));
                int rainVolume = cursor.getInt(cursor.getColumnIndex(WeatherDatabase.COLUMN_RAIN_VOLUME));
                int snowVolume = cursor.getInt(cursor.getColumnIndex(WeatherDatabase.COLUMN_SNOW_VOLUME));

                weathers.add(new WeatherForecast(cityID, cityName, countryCode, timeOfDataForecast, forecastDate,
                        currentTemperature, pressure, humidityPercent, weatherID, weatherGroup, weatherGroupDescription,
                        weatherIconID, cloudinessPercent, windSpeed, rainVolume, snowVolume));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return weathers;
    }

    private Cursor getAllEntries(String tableName){
        String[] columns = new String[] {WeatherDatabase.COLUMN_CITY_ID, WeatherDatabase.COLUMN_CITY_NAME,
                WeatherDatabase.COLUMN_COUNTRY_CODE, WeatherDatabase.COLUMN_TIME_OF_DATA_FORECAST,
                WeatherDatabase.COLUMN_FORECAST_DATE, WeatherDatabase.COLUMN_CURRENT_TEMPERATURE,
                WeatherDatabase.COLUMN_PRESSURE, WeatherDatabase.COLUMN_HUMIDITY_PERCENT,
                WeatherDatabase.COLUMN_WEATHER_ID, WeatherDatabase.COLUMN_WEATHER_GROUP,
                WeatherDatabase.COLUMN_WEATHER_GROUP_DESCRIPTION, WeatherDatabase.COLUMN_WEATHER_ICON_ID,
                WeatherDatabase.COLUMN_CLOUDINESS_PERCENT, WeatherDatabase.COLUMN_WIND_SPEED,
                WeatherDatabase.COLUMN_RAIN_VOLUME, WeatherDatabase.COLUMN_SNOW_VOLUME};
        return  database.query(tableName, columns, null, null, null, null, null);
    }

    public long getCount(String tableName){
        return DatabaseUtils.queryNumEntries(database, tableName);
    }

    public void removeAll(String tableName) {
        database.delete(tableName, null, null);
    }
}