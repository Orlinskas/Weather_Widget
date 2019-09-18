package com.orlinskas.weatherwidget.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.orlinskas.weatherwidget.preferences.Preferences;

import static com.orlinskas.weatherwidget.preferences.Preferences.SETTINGS;

public class UpdateReceiver extends BroadcastReceiver {
    public static final String UPDATE = "update";
    public final String ALARM_COUNT = "alarmCount-";

    @Override
    public void onReceive(Context context, Intent intent) {

        Preferences preferences = Preferences.getInstance(context, SETTINGS);
        int count = preferences.getData(ALARM_COUNT, 0);
        preferences.saveData(ALARM_COUNT, count + 1);

        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case UPDATE:
                case Intent.ACTION_BOOT_COMPLETED:
                    context.startService(new Intent(context, WidgetsUpdateService.class));
                    break;
            }
        }
    }
}
