package com.orlinskas.weatherwidget;

import android.content.Context;
import android.widget.Toast;

public class ToastBuilder {
    public static void create (Context context, String text){
        Toast toast = Toast.makeText(context,
                text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void createLong (Context context, String text){
        Toast toast = Toast.makeText(context,
                text, Toast.LENGTH_LONG);
        toast.show();
    }
}