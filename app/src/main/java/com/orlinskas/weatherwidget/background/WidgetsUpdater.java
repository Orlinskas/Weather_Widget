package com.orlinskas.weatherwidget.background;

import android.content.Context;

import com.orlinskas.weatherwidget.specification.WidgetEmptySpecification;
import com.orlinskas.weatherwidget.widget.Widget;
import com.orlinskas.weatherwidget.widget.WidgetRepository;

import java.util.ArrayList;

public class WidgetsUpdater {
    private Context context;

    public WidgetsUpdater(Context context) {
        this.context = context;
    }

    public void update() {
        WidgetRepository widgetRepository = new WidgetRepository(context);
        ArrayList<Widget> widgets = new ArrayList<>();
        try {
            widgets = widgetRepository.query(new WidgetEmptySpecification());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Widget widget : widgets) {
            int widgetID = widget.getId();
            WidgetUpdateChecker updateChecker = new WidgetUpdateChecker(widgetID, context);
            if(updateChecker.check()) {
                WidgetUpdater updater = new WidgetUpdater();
                updater.doUpdate(widgetID, context);
            }
        }

        setAlarmToNewUpdate();
    }

    private void setAlarmToNewUpdate() {
        AlarmManagerSetter managerSetter = new AlarmManagerSetter();
        managerSetter.setAlarm(context);
    }
}
