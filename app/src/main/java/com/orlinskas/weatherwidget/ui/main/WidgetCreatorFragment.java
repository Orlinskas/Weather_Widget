package com.orlinskas.weatherwidget.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;

import com.orlinskas.weatherwidget.R;

public class WidgetCreatorFragment extends Fragment {
    Spinner countrySpn, citySpn;
    ImageView alertCountry, alertCity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_widget_creator, container, false);
        countrySpn = root.findViewById(R.id.fragment_city_data_generator_spn_country);
        citySpn = root.findViewById(R.id.fragment_city_data_generator_spn_city);
        alertCountry = root.findViewById(R.id.fragment_city_data_generator_iv_alert_country);
        alertCity = root.findViewById(R.id.fragment_city_data_generator_iv_alert_city);
        return root;
    }
}
