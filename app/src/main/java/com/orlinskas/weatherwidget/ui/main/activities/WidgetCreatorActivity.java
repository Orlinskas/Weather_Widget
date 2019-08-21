package com.orlinskas.weatherwidget.ui.main.activities;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orlinskas.weatherwidget.ActivityOpener;
import com.orlinskas.weatherwidget.City;
import com.orlinskas.weatherwidget.Country;
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.ToastBuilder;
import com.orlinskas.weatherwidget.location.CityFinder;
import com.orlinskas.weatherwidget.post.CountryNameWriter;
import com.orlinskas.weatherwidget.widget.WidgetCreator;
import com.orlinskas.weatherwidget.widget.WidgetRepository;

@SuppressLint("MissingPermission")
public class WidgetCreatorActivity extends AppCompatActivity {
    private Button searchLocationBtn, openListLocationsBtn, createWidgetButton;
    private TextView countryName, cityName;
    private TextView indicatorGpsOn, indicatorNetworkOn, indicatorGpsOff, indicatorNetworkOff;
    private ProgressBar progressBar;
    private Country country;
    private City city;
    private final int REQUEST_CODE_PERMISSION_FINE_LOCATION = 1001;
    private final int REQUEST_CODE_PERMISSION_COARSE_LOCATION = 1002;
    private boolean permissionGPS;
    private boolean permissionNetwork;
    private SearchLocationTask task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_creator);
        progressBar = findViewById(R.id.activity_city_data_generator_pb);
        searchLocationBtn = findViewById(R.id.activity_city_data_generator_btn_gps);
        openListLocationsBtn = findViewById(R.id.activity_city_data_generator_btn_open_list);
        createWidgetButton = findViewById(R.id.activity_city_data_generator_btn_start);

        countryName = findViewById(R.id.activity_city_data_generator_tv_country_name);
        cityName = findViewById(R.id.activity_city_data_generator_tv_city_name);

        indicatorGpsOn = findViewById(R.id.activity_city_data_generator_tv_gps_on);
        indicatorGpsOff = findViewById(R.id.activity_city_data_generator_tv_gps_off);
        indicatorNetworkOn = findViewById(R.id.activity_city_data_generator_tv_network_on);
        indicatorNetworkOff = findViewById(R.id.activity_city_data_generator_tv_network_off);

        checkGPSOn();
        checkNetworkOn();

        final Animation buttonClickAnim = AnimationUtils.loadAnimation(this, R.anim.animation_button);

        searchLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClickAnim);
                if(isSearchTaskRunning()){
                    ToastBuilder.create(getApplicationContext(), "Поиск уже начат, пожождите..");
                }
                else {
                    permissionGPS = checkPermissionGPSLocation();
                    permissionNetwork = checkPermissionNetworkLocation();

                    if (!permissionGPS) {
                        toAskPermissionGPSLocation();
                    }
                    if (!permissionNetwork) {
                        toAskPermissionNetworkLocation();
                    }

                    String provider = chooseAvailableProvider();

                    if (provider == null) {
                        ToastBuilder.create(getApplicationContext(), "Разрешите доступ или включите GPS");
                    } else {
                        runSearchLocationTask(provider);
                    }
                }
            }
        });

        openListLocationsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClickAnim);
                Intent intent = new Intent(getApplicationContext(), CountryListActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        createWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClickAnim);
                createWidget();
            }
        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            city = (City) data.getSerializableExtra("city");
            country = (Country) data.getSerializableExtra("country");
        }
        checkAndShowLocationData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNetworkOn();
        checkGPSOn();
    }

    @Override
    protected void onPause() {
        super.onPause();
        checkNetworkOn();
        checkGPSOn();
        if(isSearchTaskRunning()) {
            stopSearchLocationTask();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isSearchTaskRunning()) {
            stopSearchLocationTask();
        }
    }


    private boolean checkGPSOn() {
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

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
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

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
        if(checkGPSOn() & checkPermissionGPSLocation()) {
            return LocationManager.GPS_PROVIDER;
        }
        if (checkNetworkOn() & checkPermissionNetworkLocation()) {
            return LocationManager.NETWORK_PROVIDER;
        }
        else {
            return null;
        }
    }

    private void runSearchLocationTask(String provider) {
        task = new SearchLocationTask(provider);
        task.execute();
    }

    private boolean isSearchTaskRunning() {
        if(task == null){
            return false;
        }
        return task.getStatus() == AsyncTask.Status.RUNNING;
    }

    private void stopSearchLocationTask() {
        task.cancel(true);
        task = null;
    }

    @SuppressLint("StaticFieldLeak")
    private class SearchLocationTask extends AsyncTask<Void, Void, City> {
        private Location location;
        private String provider;
        private LocationManager locationManager;

        SearchLocationTask(String provider) {
            this.provider = provider;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            ToastBuilder.create(getApplicationContext(), "Поиск..");

            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
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
            locationManager.requestLocationUpdates(provider, 50, 10, locationListener);
        }

        @Override
        protected City doInBackground(Void... voids) {
            while (true) {
                if(location != null){
                    break;
                }
                if(isCancelled()){
                    break;
                }
            }
            CityFinder cityFinder = new CityFinder(getApplicationContext(), location);
            return cityFinder.find();
        }

        private void setLocation(Location location) {
            this.location = location;
        }

        @Override
        protected void onPostExecute(City city) {
            super.onPostExecute(city);
            progressBar.setVisibility(View.INVISIBLE);
            showResultSearchTask(city);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

    private void showResultSearchTask(City city) {
        if(city == null) {
            ToastBuilder.create(getApplicationContext(), "Не найдено");
        }
        else {
            this.city = city;
            CountryNameWriter nameWriter = new CountryNameWriter();
            this.country = new Country(city.getCountryCode(), nameWriter.findNameWith(city.getCountryCode()));
            checkAndShowLocationData();
        }
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


    private void createWidget() {
        if(isCorrectnessWidgetData()) {
            try {
                WidgetCreator widgetCreator = new WidgetCreator();
                WidgetRepository widgetRepository = new WidgetRepository(getApplicationContext());
                if(!widgetRepository.add(widgetCreator.create(city))){
                    ToastBuilder.create(getApplicationContext(),"Не удалось создать");
                }
                else {
                    ActivityOpener.openActivity(getApplicationContext(),MainActivity.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ToastBuilder.create(getApplicationContext(),"Ошибка создания виджета");
            }
        }
    }

    private boolean isCorrectnessWidgetData() {
        if(country != null & city != null) {
            if(country.getCode().equals(city.getCountryCode())) {
                return true;
            }
            else {
                ToastBuilder.create(getApplicationContext(), "Не корректное местоположение");
                return false;
            }
        }
        else {
            ToastBuilder.create(getApplicationContext(), "Не указано местоположение");
            return false;
        }

    }

}
