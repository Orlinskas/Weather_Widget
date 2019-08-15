package com.orlinskas.weatherwidget.widget;

import android.content.Context;

import com.orlinskas.weatherwidget.specification.WidgetSpecification;

import java.util.ArrayList;

public class WidgetRepository {
    private WidgetSerializer serializer;

    public WidgetRepository(Context context) {
        serializer = new WidgetSerializer(context);
    }

    public boolean add(Widget widget) {
        try {
            WidgetsList widgetsList = serializer.loadList();
            ArrayList<Widget> widgets = widgetsList.getWidgets();
            widgets.add(widget);
            widgetsList.setWidgets(widgets);
            serializer.saveList(widgetsList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Widget widget) {
        try {
            WidgetsList widgetsList = serializer.loadList();
            ArrayList<Widget> widgets = widgetsList.getWidgets();
            int index = widgets.indexOf(widget);
            widgets.remove(widget);
            widgets.add(index,widget);
            widgetsList.setWidgets(widgets);
            serializer.saveList(widgetsList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remote(Widget widget) {
        try {
            WidgetsList widgetsList = serializer.loadList();
            ArrayList<Widget> widgets = widgetsList.getWidgets();
            widgets.remove(widget);
            widgetsList.setWidgets(widgets);
            serializer.saveList(widgetsList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Widget> query(WidgetSpecification specification) {
        ArrayList<Widget> queryWidgets = new ArrayList<>();

        try {
            WidgetsList widgetsList = serializer.loadList();
            ArrayList<Widget> widgets = widgetsList.getWidgets();

            for (Widget widget : widgets) {
                if(specification.specified(widget)){
                    queryWidgets.add(widget);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return queryWidgets;
    }

}
