package com.orlinskas.weatherwidget.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.FirstRunner;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.ToastBuilder;
import com.orlinskas.weatherwidget.preferences.FirstRunVerifier;
import com.orlinskas.weatherwidget.request.Request;
import com.orlinskas.weatherwidget.request.RequestBuilder;
import com.orlinskas.weatherwidget.request.RequestURLGenerator;
import com.orlinskas.weatherwidget.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Заполните таблицу", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        processFirstRun(getApplicationContext());
    }

    private void processFirstRun(Context applicationContext) {
        ToastBuilder.create(applicationContext, "Открытие базы данных...");
        FirstRunVerifier firstRunVerifier = new FirstRunVerifier(applicationContext);

        if(!firstRunVerifier.check()) {
            FirstRunner firstRunner = new FirstRunner(applicationContext);
            firstRunner.doFirstRun();
            firstRunVerifier.setFirstRun(true);
        }
        ToastBuilder.create(applicationContext, "Готово!");
    }


}