package com.orlinskas.weatherwidget.post;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.json.JSONInputSteamCreator;
import com.orlinskas.weatherwidget.json.JSONLineParser;
import com.orlinskas.weatherwidget.json.JSONSteamReader;

import java.io.InputStream;
import java.util.ArrayList;
/**
 * Класс не будет работать в релиз версии, создан для генерации
 * базы данных городов из документа предоставляемым API.
 */
public class CityDataJsonParser {
    private ArrayList<City> cities = new ArrayList<>();

    public ArrayList<City> parseJSONFile(int path) {
        JSONInputSteamCreator steam = new JSONInputSteamCreator();
        JSONSteamReader steamReader = new JSONSteamReader();
        JSONLineParser lineParser = new JSONLineParser();

        try {
            InputStream inputStream = steam.create(path);
            String json = steamReader.read(inputStream);
            cities = lineParser.parse(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }

}
