package com.orlinskas.weatherwidget.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.orlinskas.weatherwidget.Country;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.presenters.CountryListPresenter;

import java.util.ArrayList;

public class CountryListActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        listView = findViewById(R.id.fragment_country_list_lv);

        ArrayList<Country> countries;
        CountryListPresenter presenter = new CountryListPresenter(getApplicationContext());
        countries = presenter.present();

        ArrayAdapter<Country> adapter = new CountryListAdapter(getApplicationContext(),
                R.layout.country_row, countries);

        listView.setAdapter(adapter);

    }

    private class CountryListAdapter extends ArrayAdapter<Country> {
        ArrayList<Country> countries;

        CountryListAdapter(Context context, int textViewResourceId, ArrayList<Country> countries) {
            super(context, textViewResourceId, countries);
            this.countries = countries;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            @SuppressLint("ViewHolder") View row = inflater.inflate(R.layout.country_row, parent, false);
            TextView countryName = row.findViewById(R.id.county_row_tv_name);
            TextView countryCode = row.findViewById(R.id.county_row_tv_code);

            String name = countries.get(position).getName();
            String code = countries.get(position).getCode();

            countryName.setText(name);
            countryCode.setText(code);

            return row;
        }
    }
}
