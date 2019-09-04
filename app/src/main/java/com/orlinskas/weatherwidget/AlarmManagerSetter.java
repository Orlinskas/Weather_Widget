package com.orlinskas.weatherwidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

import static com.orlinskas.weatherwidget.UpdateReceiver.UPDATE;

public class AlarmManagerSetter {
    public void setAlarm(Context context) {
        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, UpdateReceiver.class);
        intent.setAction(UPDATE);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar timeToRepeat = Calendar.getInstance();
        timeToRepeat.setTimeInMillis(System.currentTimeMillis());
        timeToRepeat.add(Calendar.HOUR, 1);

        if (Build.VERSION.SDK_INT >= 23) {
            alarm.setExactAndAllowWhileIdle(AlarmManager.RTC, timeToRepeat.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 19) {
            alarm.setExact(AlarmManager.RTC, timeToRepeat.getTimeInMillis(), pendingIntent);
        } else {
            alarm.set(AlarmManager.RTC, timeToRepeat.getTimeInMillis(), pendingIntent);
        }
    }
}
