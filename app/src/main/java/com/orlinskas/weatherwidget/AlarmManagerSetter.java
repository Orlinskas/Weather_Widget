package com.orlinskas.weatherwidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmManagerSetter {
    private final String UPDATE = "update";

    public void setAlarm(Context context) {
        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, UpdateReceiver.class);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar timeToRepeat = Calendar.getInstance();
        timeToRepeat.setTimeInMillis(System.currentTimeMillis());
        timeToRepeat.add(Calendar.HOUR, 1);

        alarm.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), timeToRepeat.getTimeInMillis(), pendingIntent);
    }
}
