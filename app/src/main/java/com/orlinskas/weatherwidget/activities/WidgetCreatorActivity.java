package com.orlinskas.weatherwidget.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.Country;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.ToastBuilder;
import com.orlinskas.weatherwidget.location.CityFinder;

import java.security.Provider;

@SuppressLint("MissingPermission")
public class WidgetCreatorActivity extends AppCompatActivity {
    private Button findLocationBtn, openListBtn, createWidgetButton;
    private TextView countryName, cityName;
    private TextView indicatorGpsOn, indicatorNetworkOn, indicatorGpsOff, indicatorNetworkOff;
    private ProgressBar progressBar;
    private Country country;
    private City city;
    private LocationManager locationManager;
    private final int REQUEST_CODE_PERMISSION_FINE_LOCATION = 1001;
    private final int REQUEST_CODE_PERMISSION_COARSE_LOCATION = 1002;
    private boolean permissionGPS;
    private boolean permissionNetwork;
    Location location;
    Provider provider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_widget_creator);
        findLocationBtn = findViewById(R.id.fragment_city_data_generator_btn_gps);
        openListBtn = findViewById(R.id.fragment_city_data_generator_btn_open_list);
        countryName = findViewById(R.id.fragment_city_data_generator_tv_country_name);
        cityName = findViewById(R.id.fragment_city_data_generator_tv_city_name);
        progressBar = findViewById(R.id.fragment_city_data_generator_pb);
        createWidgetButton = findViewById(R.id.fragment_city_data_generator_btn_start);
        indicatorGpsOn = findViewById(R.id.fragment_city_data_generator_tv_gps_on);
        indicatorGpsOff = findViewById(R.id.fragment_city_data_generator_tv_gps_off);
        indicatorNetworkOn = findViewById(R.id.fragment_city_data_generator_tv_network_on);
        indicatorNetworkOff = findViewById(R.id.fragment_city_data_generator_tv_network_off);

        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    setLocation(location);
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    setLocation(locationManager.getLastKnownLocation(provider));
                }
                @Override
                public void onProviderEnabled(String provider) {
                    setLocation(locationManager.getLastKnownLocation(provider));
                }
                @Override
                public void onProviderDisabled(String provider) {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, locationListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

        checkGPSOn();
        checkNetworkOn();

        findLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionGPS = checkPermissionGPSLocation();
                permissionNetwork = checkPermissionNetworkLocation();

                if (!permissionGPS) {
                    toAskPermissionGPSLocation();
                }
                if (!permissionNetwork) {
                    toAskPermissionNetworkLocation();
                }
                if (!checkGPSOn() & !checkNetworkOn()){
                    ToastBuilder.create(getApplicationContext(), "Включите GPS или интернет");
                }

                String provider = chooseAvailableProvider();

                if (provider == null) {
                    ToastBuilder.create(getApplicationContext(), "Разрешите доступ или включите GPS");
                } else {
                    findLocation(provider);
                }
            }
        });

        openListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CountryListActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        createWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWidget();
            }
        });
    }

    private boolean checkGPSOn() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            setOnIndicatorGPS();
            return true;
        } else {
            setOffIndicatorGPS();
            return false;
        }
    }

    private void setOnIndicatorGPS() {
        indicatorGpsOn.setVisibility(View.VISIBLE);
        indicatorGpsOff.setVisibility(View.INVISIBLE);
    }

    private void setOffIndicatorGPS() {
        indicatorGpsOff.setVisibility(View.VISIBLE);
        indicatorGpsOn.setVisibility(View.INVISIBLE);
    }

    private boolean checkNetworkOn() {
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            setOnIndicatorNetwork();
            return true;
        } else {
            setOffIndicatorNetwork();
            return false;
        }

    }

    private void setOnIndicatorNetwork() {
        indicatorNetworkOn.setVisibility(View.VISIBLE);
        indicatorNetworkOff.setVisibility(View.INVISIBLE);
    }

    private void setOffIndicatorNetwork() {
        indicatorNetworkOff.setVisibility(View.VISIBLE);
        indicatorNetworkOn.setVisibility(View.INVISIBLE);

    }

    private boolean checkPermissionGPSLocation() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkPermissionNetworkLocation() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void toAskPermissionGPSLocation() {
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_PERMISSION_FINE_LOCATION);
        }
    }

    private void toAskPermissionNetworkLocation() {
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_PERMISSION_COARSE_LOCATION);
        }
    }

    private String chooseAvailableProvider() {
        if (checkNetworkOn() & checkPermissionNetworkLocation()) {
            return LocationManager.NETWORK_PROVIDER;
        }
        if(checkGPSOn() & checkPermissionGPSLocation()) {
            return LocationManager.GPS_PROVIDER;
        }
        else {
            return null;
        }
    }

    private void findLocation(String provider) {
        FindLocationTask task = new FindLocationTask();
        task.execute();
    }

    private void findCityFromLocation(Location location) {
        CityFinder cityFinder = new CityFinder(getApplicationContext(), location);
        City city = cityFinder.find();
    }

    @SuppressLint("StaticFieldLeak")
    private class FindLocationTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            ToastBuilder.create(getApplicationContext(), "Поиск..");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                if(location != null){
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.INVISIBLE);
            findCityFromLocation(location);
        }
    }

    private void setLocation(Location location) {
        this.location = location;
    }

    private void createWidget() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_FINE_LOCATION:
                permissionGPS = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;

            case REQUEST_CODE_PERMISSION_COARSE_LOCATION:
                permissionNetwork = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
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

    @Override
    public void onResume() {
        super.onResume();
        checkNetworkOn();
        checkGPSOn();
    }

    @Override
    public void onPause() {
        super.onPause();
        checkNetworkOn();
        checkGPSOn();
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
}
