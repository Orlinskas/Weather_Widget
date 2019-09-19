package com.orlinskas.weatherwidget.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UpdateReceiver extends BroadcastReceiver {
    public static final String UPDATE = "update";

    @Override
    public void onReceive(Context context, Intent intent) {
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
