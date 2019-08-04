package com.orlinskas.weatherwidget.response;

import android.content.Context;

import com.orlinskas.weatherwidget.Weather;
import com.orlinskas.weatherwidget.repository.WeatherRepository;

import java.util.ArrayList;

public class WeatherRepositoryWriter {
    private Context context;

    public WeatherRepositoryWriter(Context context) {
        this.context = context;
    }

    public void write(ArrayList<Weather> weathers) {
        WeatherRepository weatherRepository = new WeatherRepository(context);
        
        for(Weather weather : weathers) {
            weatherRepository.add(weather);
        }
    }
}
