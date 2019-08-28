package com.orlinskas.weatherwidget.ui.main.home;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.preferences.Preferences;
import com.orlinskas.weatherwidget.widget.Widget;
import com.orlinskas.weatherwidget.widget.WidgetRepository;

import java.util.Objects;

public class HomeWidget extends AppWidgetProvider {
    private final String TAG = this.getClass().getSimpleName();
    private int widgetID;
    private RelativeLayout parrentLayout;
    private ImageView lefnBtn, rightBtn;

    public HomeWidget() {
        super();
        Log.d(TAG,"конструктор");

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        int ID = Objects.requireNonNull(intent.getExtras()).getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
        if(ID != AppWidgetManager.INVALID_APPWIDGET_ID) {
            updateWidget(ID, context, AppWidgetManager.getInstance(context));
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int id : appWidgetIds) {
            updateWidget(id, context, appWidgetManager);
        }

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    public void updateWidget(int id, Context context, AppWidgetManager appWidgetManager) {
        int widgetID = findID(id, context);
        Widget widget = findWidget(widgetID, context);
        RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        if(widget != null){
           widgetView = updateUI(widgetView, widget);
        }

        appWidgetManager.updateAppWidget(id, widgetView);
    }

    private RemoteViews updateUI(RemoteViews widgetView, Widget widget) {
        String description = widget.getCity().getName();

        widgetView.setTextViewText(R.id.layout_widget_tv_description, description);

        return widgetView;
    }

    private Widget findWidget(int widgetID, Context context) {
        WidgetRepository repository = new WidgetRepository(context);
        try {
            return repository.find(widgetID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int findID(int id, Context context) {
        Preferences preferences = Preferences.getInstance(context,Preferences.SETTINGS);
        return preferences.getData("Widget" + id, 0);
    }
}
