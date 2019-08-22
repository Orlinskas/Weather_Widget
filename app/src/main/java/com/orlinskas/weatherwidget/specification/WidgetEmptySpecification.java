package com.orlinskas.weatherwidget.specification;

import com.orlinskas.weatherwidget.widget.Widget;

public class WidgetEmptySpecification implements Specification <Widget> {
    @Override
    public boolean specified(Widget object) {
        return true;
    }
}
