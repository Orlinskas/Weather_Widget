package com.orlinskas.weatherwidget.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.orlinskas.weatherwidget.specification.WidgetSpecification;
import com.orlinskas.weatherwidget.widget.Widget;
import com.orlinskas.weatherwidget.widget.WidgetRepository;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static Widget[] widgetsArray;
    private final Context mContext;
    private ArrayList<Widget> widgets;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        WidgetRepository widgetRepository = new WidgetRepository(mContext);
        widgets = widgetRepository.query(new WidgetSpecification());
        widgetsArray = (Widget[]) widgets.toArray();
    }

    @Override
    public Fragment getItem(int position) {
        WidgetFragment widgetFragment = new WidgetFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("widget", widgetsArray[position]);
        widgetFragment.setArguments(bundle);
        return new WidgetFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return widgetsArray[position].getCity().getName();
    }

    @Override
    public int getCount() {
        return widgetsArray.length;
    }
}