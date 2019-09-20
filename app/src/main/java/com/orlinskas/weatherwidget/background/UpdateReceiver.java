package com.orlinskas.weatherwidget.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UpdateReceiver extends BroadcastReceiver {
    public static final String UPDATE = "update";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null) {
            int widgetID = intent.getIntExtra("widgetID",0);

            switch (intent.getAction()) {
                case UPDATE:
                case Intent.ACTION_BOOT_COMPLETED:
                    Intent intentService = new Intent(context, WidgetUpdateService.class);
                    intentService.putExtra("widgetID", widgetID);
                    context.startService(intentService);
                    break;
            }

            AlarmManagerSetter alarmManagerSetter = new AlarmManagerSetter();
            alarmManagerSetter.setAlarm(context, widgetID);
        }
    }
}
