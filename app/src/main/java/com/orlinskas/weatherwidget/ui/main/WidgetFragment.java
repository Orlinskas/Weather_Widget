package com.orlinskas.weatherwidget.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.widget.Widget;

/**
 * A placeholder fragment containing a simple view.
 */
public class WidgetFragment extends Fragment {
    private Widget widget;

    public WidgetFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        return root;
    }
}