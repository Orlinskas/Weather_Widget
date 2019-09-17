package com.orlinskas.weatherwidget.background;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WidgetsUpdateService extends Service {
    public WidgetsUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        WidgetsUpdater updater = new WidgetsUpdater(this);
        updater.update();

        return super.onStartCommand(intent, flags, startId);
    }
}
