package com.orlinskas.weatherwidget.response;

import com.orlinskas.weatherwidget.date.DateFormat;
import com.orlinskas.weatherwidget.forecast.Weather;
import com.orlinskas.weatherwidget.date.DateHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONResponseParserToWeather {
    private int cityID;
    private String cityName;
    private String countryCode;
    private String timeOfDataForecast;
    private String forecastDate;
    private int currentTemperature;
    private int pressure;
    private int humidityPercent;
    private int weatherID;
    private String weatherGroup;
    private String weatherGroupDescription;
    private String weatherIconID;
    private int cloudinessPercent;
    private double windSpeed;
    private int rainVolume;
    private int snowVolume;

    public ArrayList<Weather> parse(String json) {
        ArrayList<Weather> weathers = new ArrayList<>();
        try {
            JSONObject fullJson = new JSONObject(json);

            writeCurrentDate();
            writeCityInfo(fullJson.getJSONObject("city"));

            JSONArray forecastList = fullJson.getJSONArray("list");
            for (int i = 0; i < forecastList.length(); i++) {
                JSONObject dayForecast = forecastList.getJSONObject(i);

                checkPrecipitation(dayForecast);
                timeOfDataForecast = dayForecast.getString("dt_txt");
                writeMainInfo(dayForecast.getJSONObject("main"));
                writeWeatherInfo(dayForecast.getJSONArray("weather").getJSONObject(0));
                writeCloudinessInfo(dayForecast.getJSONObject("clouds"));
                writeWindInfo(dayForecast.getJSONObject("wind"));

                weathers.add(createWeatherObject());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weathers;
    }

    private void writeCurrentDate() {
        forecastDate = DateHelper.getCurrent(DateFormat.YYYY_MM_DD);
    }

    private void writeCityInfo(JSONObject object) {
        try {
            cityID = object.getInt("id");
            cityName = object.getString("name");
            countryCode = object.getString("country");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkPrecipitation(JSONObject dayForecast) {
        if (dayForecast.has("rain")){
            try {
                writeRain(dayForecast.getJSONObject("rain"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            rainVolume = 0;
        }

        if (dayForecast.has("snow")){
            try {
                writeSnow(dayForecast.getJSONObject("snow"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            snowVolume = 0;
        }
    }

    private void writeMainInfo(JSONObject object) {
        try {
            currentTemperature = (int) object.getDouble("temp");
            pressure = (int) object.getDouble("pressure");
            humidityPercent = object.getInt("humidity");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void writeWeatherInfo(JSONObject object) {
        try {
            weatherID = object.getInt("id");
            weatherGroup = object.getString("main");
            weatherGroupDescription = object.getString("description");
            weatherIconID = object.getString("icon");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void writeCloudinessInfo(JSONObject object) {
        try {
            cloudinessPercent = object.getInt("all");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void writeWindInfo(JSONObject object) {
        try {
            windSpeed = object.getDouble("speed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void writeRain(JSONObject object) throws JSONException {
        rainVolume = (int) object.getDouble("3h");
    }

    private void writeSnow(JSONObject object) throws JSONException {
        snowVolume = (int) object.getDouble("3h");
    }

    private Weather createWeatherObject() {
        return new Weather(cityID, cityName, countryCode, timeOfDataForecast, forecastDate,
                currentTemperature, pressure, humidityPercent, weatherID, weatherGroup,
                weatherGroupDescription, weatherIconID, cloudinessPercent, windSpeed, rainVolume,
                snowVolume);
    }
}
