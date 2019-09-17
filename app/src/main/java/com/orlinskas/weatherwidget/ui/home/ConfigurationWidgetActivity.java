package com.orlinskas.weatherwidget.ui.home;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.ToastBuilder;
import com.orlinskas.weatherwidget.preferences.Preferences;
import com.orlinskas.weatherwidget.specification.WidgetEmptySpecification;
import com.orlinskas.weatherwidget.widget.Widget;
import com.orlinskas.weatherwidget.widget.WidgetRepository;

import java.util.ArrayList;

import static com.orlinskas.weatherwidget.preferences.Preferences.SETTINGS;
import static com.orlinskas.weatherwidget.preferences.Preferences.WIDGET_ID_DEPENDENCE;

public class ConfigurationWidgetActivity extends Activity {
    private int id = AppWidgetManager.INVALID_APPWIDGET_ID;
    private Intent resultValue;
    private Spinner spinnerWidgetList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            id = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (id == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
        resultValue.putExtra(HomeWidget.ACTION, HomeWidget.ACTION_CREATE);
        setResult(RESULT_CANCELED, resultValue); //сформировал негативный ответ на случай выхода

        setContentView(R.layout.config_layout);
        try {
            ArrayList<Widget> widgets = findWidgetsInRepository();
            spinnerWidgetList = findViewById(R.id.config_layout_spn);
            setSpinnerAdapter(widgets);
        } catch (Exception e) {
            e.printStackTrace();
            ToastBuilder.create(getBaseContext(),getString(R.string.greate_widget_in_app));
            finish();
        }
    }

    private ArrayList<Widget> findWidgetsInRepository() throws Exception {
        WidgetRepository repository = new WidgetRepository(this);
        return repository.query(new WidgetEmptySpecification());
    }

    private void setSpinnerAdapter(ArrayList<Widget> widgets) {
        ArrayAdapter<Widget> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, widgets);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWidgetList.setAdapter(adapter);
    }

    public void onClickCreate(View view) {
        final Animation buttonClickAnim = AnimationUtils.loadAnimation(this, R.anim.animation_button);
        view.startAnimation(buttonClickAnim);

        Widget widget = (Widget) spinnerWidgetList.getSelectedItem();
        Preferences preferences = Preferences.getInstance(this, SETTINGS);
        //запомнил соотношение - ID который дал андроид/ID моего объекта виджета
        preferences.saveData(WIDGET_ID_DEPENDENCE + id, widget.getId());

        setResult(RESULT_OK, resultValue); //отправил положительный ответ
        finish();
    }
}

