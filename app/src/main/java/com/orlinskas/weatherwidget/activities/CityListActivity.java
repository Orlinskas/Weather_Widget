package com.orlinskas.weatherwidget.activities;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.Country;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.presenters.CityListPresenter;

import java.util.ArrayList;

public class CityListActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<City> cities;
    private EditText searchCityField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        listView = findViewById(R.id.activity_city_list_lv);
        searchCityField = findViewById(R.id.activity_city_list_et);

        findCities();

        ArrayAdapter<City> adapter = new CityListActivity.CityListAdapter(getApplicationContext(), R.layout.city_row, cities);
        listView.setAdapter(adapter);

        searchCityField.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    scrollListTo(searchCityField.getText().toString());
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

            }
        });
    }

    private void findCities() {
        Country country = (Country) getIntent().getSerializableExtra("country");
        CityListPresenter presenter = new CityListPresenter(getApplicationContext());
        cities = presenter.present(country);
    }

    private class CityListAdapter extends ArrayAdapter<City> {
        ArrayList<City> cities;

        CityListAdapter(Context context, int textViewResourceId, ArrayList<City> cities) {
            super(context, textViewResourceId, cities);
            this.cities = cities;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            @SuppressLint("ViewHolder")
            View row = inflater.inflate(R.layout.city_row, parent, false);

            TextView cityName = row.findViewById(R.id.city_row_tv_name);
            String name = cities.get(position).getName();
            cityName.setText(name);

            if (position%2 == 0) {
                row.setBackgroundColor(getResources().getColor(R.color.colorRowHigh));
            }
            else {
                row.setBackgroundColor(getResources().getColor(R.color.colorRowLight));
            }

            return row;
        }
    }

    private void scrollListTo(String desiredCityNamePart) {
        int position = 0;

        for(City city : cities) {

            String currentCityNamePart = null;
            try {
                currentCityNamePart = city.getName().substring(0, desiredCityNamePart.length());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (currentCityNamePart != null ) {
                if(currentCityNamePart.equals(desiredCityNamePart)) {
                    listView.smoothScrollToPosition(position);
                    break;
                }
            }

            position++;
        }

    }
}
