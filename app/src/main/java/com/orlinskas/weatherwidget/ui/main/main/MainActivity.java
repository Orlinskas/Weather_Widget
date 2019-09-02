package com.orlinskas.weatherwidget.ui.main.main;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.orlinskas.weatherwidget.ActivityOpener;
import com.orlinskas.weatherwidget.FirstRunner;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.ToastBuilder;
import com.orlinskas.weatherwidget.preferences.FirstRunVerifier;
import com.orlinskas.weatherwidget.ui.main.other.WidgetCreatorActivity;
import com.orlinskas.weatherwidget.ui.main.widget.AnimatedBackgroundView;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOpener.openActivity(getApplicationContext(), WidgetCreatorActivity.class);
            }
        });

        processFirstRun(getApplicationContext());

        RelativeLayout relativeLayout = findViewById(R.id.activity_main_rl_background_anim);
        relativeLayout.addView(new AnimatedBackgroundView(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void processFirstRun(Context applicationContext) {
        FirstRunVerifier firstRunVerifier = new FirstRunVerifier(applicationContext);

        if(!firstRunVerifier.check()) {
            ToastBuilder.createSnackBar(viewPager, getString(R.string.open_data));

            FirstRunner firstRunner = new FirstRunner(applicationContext);
            firstRunner.doFirstRun();

            firstRunVerifier.setFirstRun(true);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}