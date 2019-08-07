package com.orlinskas.weatherwidget.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.orlinskas.weatherwidget.Country;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.presenters.CountryListPresenter;

import java.util.ArrayList;

public class CountryListActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Country> countries;
    private EditText searchCountryField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        listView = findViewById(R.id.activity_country_list_lv);
        searchCountryField = findViewById(R.id.activity_country_list_et);

        findCountries();

        ArrayAdapter<Country> adapter = new CountryListAdapter(getApplicationContext(), R.layout.country_row, countries);
        listView.setAdapter(adapter);

        searchCountryField.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    scrollListTo(searchCountryField.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openCityListActivity(position);
            }
        });
    }

    private void findCountries() {
        CountryListPresenter presenter = new CountryListPresenter(getApplicationContext());
        countries = presenter.present();
    }

    private void openCityListActivity(int positionCountryItemInList) {
        Country country = countries.get(positionCountryItemInList);
        Intent intent = new Intent(getApplicationContext(), CityListActivity.class);
        intent.putExtra("country", country);
        startActivity(intent);
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
            TextView countryName = row.findViewById(R.id.country_row_tv_name);
            TextView countryCode = row.findViewById(R.id.county_row_tv_code);

            String name = countries.get(position).getName();
            String code = countries.get(position).getCode();

            countryName.setText(name);
            countryCode.setText(code);

            if (position%2 == 0) {
                try {
                    row.setBackgroundColor(getResources().getColor(R.color.colorRowHigh));
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
            else{
                row.setBackgroundColor(getResources().getColor(R.color.colorRowLight));
            }

            return row;
        }
    }

    private void scrollListTo(String desiredCountryNamePart) {
        int position = 0;

        for(Country country : countries) {

            String currentCountryNamePart = null;
            try {
                currentCountryNamePart = country.getName().substring(0, desiredCountryNamePart.length());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (currentCountryNamePart != null ) {
                if(currentCountryNamePart.equals(desiredCountryNamePart)) {
                    listView.smoothScrollToPosition(position);
                    break;
                }
            }

            position++;
        }

    }
}
