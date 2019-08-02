package com.orlinskas.weatherwidget.request;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class RequestURLGenerator {
    public static URL generate(Request request) {
        Uri buildRequest = Uri.parse(request.getSource() + request.getForecastType())
                .buildUpon()
                .appendQueryParameter("id", String.valueOf(request.getCity().getId()))
                .appendQueryParameter("APPID", request.getApiKey())
                .appendQueryParameter("units", request.getUnitsType())
                .build();

        URL requestOpenWeather = null;
        try {
            requestOpenWeather = new URL(buildRequest.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return requestOpenWeather;
    }
}
