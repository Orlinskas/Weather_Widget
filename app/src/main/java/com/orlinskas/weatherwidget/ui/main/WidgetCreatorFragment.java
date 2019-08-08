package com.orlinskas.weatherwidget.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.Country;
import com.orlinskas.weatherwidget.R;

import com.orlinskas.weatherwidget.ToastBuilder;
import com.orlinskas.weatherwidget.activities.CountryListActivity;

public class WidgetCreatorFragment extends Fragment {
    private Button findLocationBtn, openListBtn, createWidgetButton;
    private TextView countryName, cityName;
    private ProgressBar progressBar;
    private Country country;
    private City city;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_widget_creator, container, false);
        findLocationBtn = root.findViewById(R.id.fragment_city_data_generator_btn_gps);
        openListBtn = root.findViewById(R.id.fragment_city_data_generator_btn_open_list);
        countryName = root.findViewById(R.id.fragment_city_data_generator_tv_country_name);
        cityName = root.findViewById(R.id.fragment_city_data_generator_tv_city_name);
        progressBar = root.findViewById(R.id.fragment_city_data_generator_pb);
        createWidgetButton = root.findViewById(R.id.fragment_city_data_generator_btn_start);

        findLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastBuilder.create(getContext(),"Еще не добавлено");
            }
        });

        openListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CountryListActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        createWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWidget();
            }
        });

        return root;
    }

    private void checkAndShowLocationData() {
        if(isHasCountry()) {
            showCountryName();
            if(isHasCity()) {
                showCityName();
            }
        }
    }

    private boolean isHasCountry() {
        return country != null;
    }

    private boolean isHasCity() {
        return city != null;
    }

    private void showCountryName() {
        countryName.setText(country.getName());
    }

    private void showCityName() {
        cityName.setText(city.getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            city = (City) data.getSerializableExtra("city");
            country = (Country) data.getSerializableExtra("country");
        }
        checkAndShowLocationData();
    }

    private void createWidget() {

    }
}
