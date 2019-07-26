package com.orlinskas.weatherwidget.json;

import com.orlinskas.weatherwidget.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONLineParser {
    private ArrayList<City> cities = new ArrayList<>();

    public ArrayList<City> parse(String json) {

        try {
            JSONArray jsonCities = new JSONArray(json);

            int index = 0;

            do {
                JSONObject jsonCity = (JSONObject) jsonCities.get(index);
                City city;
                city = getCity(jsonCity);
                cities.add(city);
                index++;
            }while (index < jsonCities.length());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cities;
    }

    private City getCity(JSONObject jsonCity) {
        int id;
        String name;
        String countryCode;
        double coordLon;
        double coordLat;

        try {
            JSONObject jsonCoord = jsonCity.getJSONObject("coord");

            id = jsonCity.getInt("id");
            name = jsonCity.getString("name");
            countryCode = jsonCity.getString("country");
            coordLon = jsonCoord.getDouble("lon");
            coordLat = jsonCoord.getDouble("lat");

            return new City(id, name, countryCode, coordLon, coordLat);
        } catch (JSONException e) {
            e.printStackTrace();
            return new City(0, "error", "ER", 0.0, 0.0);
        }
    }
}
