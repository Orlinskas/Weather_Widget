package com.orlinskas.weatherwidget.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

import static com.orlinskas.weatherwidget.background.Settings.ALARM_INTERVAL;
import static com.orlinskas.weatherwidget.background.UpdateReceiver.UPDATE;

public class AlarmManagerSetter {
    public void setAlarm(Context context) {
        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, UpdateReceiver.class);
        intent.setAction(UPDATE);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar timeToRepeat = Calendar.getInstance();
        timeToRepeat.setTimeInMillis(System.currentTimeMillis());
        timeToRepeat.add(Calendar.HOUR, ALARM_INTERVAL);

        if (Build.VERSION.SDK_INT >= 23) {
            alarm.setExactAndAllowWhileIdle(AlarmManager.RTC, timeToRepeat.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 19) {
            alarm.setExact(AlarmManager.RTC, timeToRepeat.getTimeInMillis(), pendingIntent);
        } else {
            alarm.set(AlarmManager.RTC, timeToRepeat.getTimeInMillis(), pendingIntent);
        }
    }
}
