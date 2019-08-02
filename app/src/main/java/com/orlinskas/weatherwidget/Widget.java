package com.orlinskas.weatherwidget;

import com.orlinskas.weatherwidget.request.Request;
import com.orlinskas.weatherwidget.request.RequestBuilder;

import java.io.Serializable;
import java.util.Objects;

public class Widget implements Serializable {
    private int id;
    private String source = RequestBuilder.OPEN_WEATHER;
    private City city;
    private Request request;

    public Widget(int id, City city) {
        this.id = id;
        this.city = city;
        this.request = new RequestBuilder().build(source,city);
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
