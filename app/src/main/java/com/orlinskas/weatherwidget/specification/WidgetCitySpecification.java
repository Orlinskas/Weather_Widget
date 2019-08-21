package com.orlinskas.weatherwidget.specification;

import com.orlinskas.weatherwidget.widget.Widget;

public class WidgetCitySpecification implements Specification <Widget> {
    private Widget widget;

    public WidgetCitySpecification(Widget widget) {
        this.widget = widget;
    }

    @Override
    public boolean specified(Widget object) {
        return widget.getCity().equals(object.getCity());
    }
}
