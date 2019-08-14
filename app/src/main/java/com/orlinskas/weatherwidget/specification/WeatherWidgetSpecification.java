package com.orlinskas.weatherwidget.specification;

import com.orlinskas.weatherwidget.widget.Widget;

import static com.orlinskas.weatherwidget.data.WeatherDatabase.COLUMN_CITY_ID;
import static com.orlinskas.weatherwidget.data.WeatherDatabase.TABLE_WEATHER;

public class WeatherWidgetSpecification implements SqlSpecification {
    private Widget widget;

    public WeatherWidgetSpecification(Widget widget) {
        this.widget = widget;
    }

    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM %1$s WHERE %2$s == %3$s;",
                TABLE_WEATHER,
                COLUMN_CITY_ID,
                widget.getCity().getId()
        );
    }
}
