package com.orlinskas.weatherwidget.ui.main.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.ToastBuilder;
import com.orlinskas.weatherwidget.preferences.Preferences;

public class HelpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Preferences preferences = Preferences.getInstance(getApplicationContext(), Preferences.UPDATES_COUNT);
        int count = preferences.getData("Count",0);

        ToastBuilder.create(getApplicationContext(),"Запросов" + count);
    }
}
