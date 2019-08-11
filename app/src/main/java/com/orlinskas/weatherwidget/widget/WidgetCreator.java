package com.orlinskas.weatherwidget.widget;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.math.Random;
import com.orlinskas.weatherwidget.request.Request;
import com.orlinskas.weatherwidget.request.RequestBuilder;

public class WidgetCreator {
    public Widget create(City city) {
        int id = Random.getID();

        RequestBuilder builder = new RequestBuilder();
        Request request = builder.build(city);

        return new Widget(id, city,request);
    }
}
