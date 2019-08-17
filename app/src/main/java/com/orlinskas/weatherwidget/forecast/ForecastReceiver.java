package com.orlinskas.weatherwidget.forecast;

import android.content.Context;

import com.orlinskas.weatherwidget.request.RequestURLGenerator;
import com.orlinskas.weatherwidget.request.RequestURLSender;
import com.orlinskas.weatherwidget.response.JSONResponseParserToWeather;
import com.orlinskas.weatherwidget.response.WeatherRepositoryWriter;
import com.orlinskas.weatherwidget.widget.Widget;

import java.net.URL;
import java.util.ArrayList;

public class ForecastReceiver {
    private Context context;
    private Widget widget;

    public ForecastReceiver(Context context, Widget widget) {
        this.context = context;
        this.widget = widget;
    }

    public void receive() throws Exception{
        RequestURLGenerator urlGenerator = new RequestURLGenerator();
        URL requestURL = urlGenerator.generate(widget.getRequest());
        RequestURLSender urlSender = new RequestURLSender();
        String response = urlSender.send(requestURL);
        JSONResponseParserToWeather parser = new JSONResponseParserToWeather();
        ArrayList<Weather> weathers = parser.parse(response);
        WeatherRepositoryWriter repositoryWriter = new WeatherRepositoryWriter(context);
        repositoryWriter.write(weathers);
    }
}
