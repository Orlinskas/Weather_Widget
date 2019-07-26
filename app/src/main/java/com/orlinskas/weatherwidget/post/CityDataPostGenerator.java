package com.orlinskas.weatherwidget.post;

import android.util.Log;

import com.orlinskas.weatherwidget.ApplicationContext;
import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.data.CitiesDatabase;
import com.orlinskas.weatherwidget.data.CitiesDatabaseAdapter;
import com.orlinskas.weatherwidget.json.JSONInputSteamCreator;
import com.orlinskas.weatherwidget.json.JSONLineParser;
import com.orlinskas.weatherwidget.json.JSONSteamReader;

import java.io.InputStream;
import java.util.ArrayList;
/**
 * Класс не будет работать в релиз версии, создан для генерации
 * базы данных городов из документа предоставляемым API.
 */
public class CityDataPostGenerator {
    private InputStream inputStream;
    private String json;
    private ArrayList<City> cities = new ArrayList<>();

    public void generate() {
        JSONInputSteamCreator steam = new JSONInputSteamCreator();
        JSONSteamReader steamReader = new JSONSteamReader();
        JSONLineParser lineParser = new JSONLineParser();

        try {
            inputStream = steam.create(R.raw.city_list_min);
            json = steamReader.read(inputStream);
            cities = lineParser.parse(json);

            writeToDatabase(cities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToDatabase(ArrayList<City> cities) {
        CitiesDatabaseAdapter adapter = new CitiesDatabaseAdapter(ApplicationContext.getAppContext());
        adapter.openWithTransaction();

        for(City city : cities) {
            adapter.insert(city, CitiesDatabase.TABLE_CITY);
        }

        adapter.closeWithTransaction();
    }
}
