package com.orlinskas.weatherwidget.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.orlinskas.weatherwidget.R;

public class CountryListFragment extends Fragment {
    private ListView listView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_widget_creator, container, false);

        

        return root;
    }
}
