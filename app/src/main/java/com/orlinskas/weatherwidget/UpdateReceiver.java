package com.orlinskas.weatherwidget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.orlinskas.weatherwidget.widget.WidgetsUpdater;

public class UpdateReceiver extends BroadcastReceiver {
    public static final String UPDATE = "update";

    @Override
    public void onReceive(Context context, Intent intent) {
        WidgetsUpdater updater = new WidgetsUpdater(context);

        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case UPDATE:
                case Intent.ACTION_BOOT_COMPLETED:
                    updater.update();
                    break;
            }
        }
    }
}
