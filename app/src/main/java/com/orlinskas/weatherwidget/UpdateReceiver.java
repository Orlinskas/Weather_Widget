package com.orlinskas.weatherwidget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.orlinskas.weatherwidget.widget.WidgetsUpdater;

public class UpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WidgetsUpdater updater = new WidgetsUpdater();
        updater.update(context);
    }
}
