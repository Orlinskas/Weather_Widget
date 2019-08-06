package com.orlinskas.weatherwidget.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orlinskas.weatherwidget.ActivityOpener;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.activities.CityListActivity;
import com.orlinskas.weatherwidget.activities.CountryListActivity;

public class WidgetCreatorFragment extends Fragment {
    Button countryListBtn, cityListBtn;
    ImageView alertCountry, alertCity;
    TextView countryName, cityName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_widget_creator, container, false);
        countryListBtn = root.findViewById(R.id.fragment_city_data_generator_btn_country);
        cityListBtn = root.findViewById(R.id.fragment_city_data_generator_btn_city);
        alertCountry = root.findViewById(R.id.fragment_city_data_generator_iv_alert_country);
        alertCity = root.findViewById(R.id.fragment_city_data_generator_iv_alert_city);
        countryName = root.findViewById(R.id.fragment_city_data_generator_tv_country_name);
        cityName = root.findViewById(R.id.fragment_city_data_generator_tv_city_name);

        countryListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOpener.openActivity(getContext(), CountryListActivity.class);
            }
        });

        cityListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return root;
    }
}
