package com.orlinskas.weatherwidget;

import com.orlinskas.weatherwidget.request.Request;

import java.io.Serializable;
import java.util.Objects;

public class Widget implements Serializable {
    private int id;
    private double id2;
    private String source = "OpenWeather";
    private City city;
    private Request request;

    public Widget(int id, City city, Request request) {
        this.id = id;
        this.city = city;
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Widget)) return false;
        Widget widget = (Widget) o;
        return getId() == widget.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void setCity(City city) {
        this.city = city;
    }

    private City getCity() {
        return city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Request getRequest() {
        return request;
    }
}
