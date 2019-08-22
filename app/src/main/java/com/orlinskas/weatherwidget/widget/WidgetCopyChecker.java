package com.orlinskas.weatherwidget.widget;

import android.content.Context;

import com.orlinskas.weatherwidget.specification.WidgetEmptySpecification;

import java.util.ArrayList;

public class WidgetCopyChecker {
    private Context context;
    private Widget widget;

    public WidgetCopyChecker(Context context, Widget widget) {
        this.context = context;
        this.widget = widget;
    }

    public boolean check() throws Exception {
        WidgetRepository repository = new WidgetRepository(context);
        ArrayList<Widget> widgets = repository.query(new WidgetEmptySpecification());

        for(Widget widget : widgets) {
            if(widget.equals(this.widget)){
                return true;
            }
        }

        return false;
    }
}
