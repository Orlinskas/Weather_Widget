package com.orlinskas.weatherwidget.ui.main;

import android.content.Intent;

import android.location.LocationManager;
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

import java.util.Objects;

import static android.content.Context.LOCATION_SERVICE;

public class WidgetCreatorFragment extends Fragment {
    private Button findLocationBtn, openListBtn, createWidgetButton;
    private TextView countryName, cityName;
    private TextView indicatorGpsOn, indicatorNetworkOn, indicatorGpsOff, indicatorNetworkOff;
    private ProgressBar progressBar;
    private Country country;
    private City city;
    private LocationManager locationManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_widget_creator, container, false);
        findLocationBtn = root.findViewById(R.id.fragment_city_data_generator_btn_gps);
        openListBtn = root.findViewById(R.id.fragment_city_data_generator_btn_open_list);
        countryName = root.findViewById(R.id.fragment_city_data_generator_tv_country_name);
        cityName = root.findViewById(R.id.fragment_city_data_generator_tv_city_name);
        progressBar = root.findViewById(R.id.fragment_city_data_generator_pb);
        createWidgetButton = root.findViewById(R.id.fragment_city_data_generator_btn_start);
        indicatorGpsOn = root.findViewById(R.id.fragment_city_data_generator_tv_gps_on);
        indicatorGpsOff = root.findViewById(R.id.fragment_city_data_generator_tv_gps_off);
        indicatorNetworkOn = root.findViewById(R.id.fragment_city_data_generator_tv_network_on);
        indicatorNetworkOff = root.findViewById(R.id.fragment_city_data_generator_tv_network_off);

        locationManager = (LocationManager) Objects.requireNonNull(getContext()).getSystemService(LOCATION_SERVICE);

        checkNetwork();
        checkGPS();

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
        /* @Override
  protected void onResume() {
    super.onResume();
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
        1000 * 10, 10, locationListener);
    locationManager.requestLocationUpdates(
        LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
        locationListener);
    checkEnabled();
  }

  @Override
  protected void onPause() {
    super.onPause();
    locationManager.removeUpdates(locationListener);
  }

  private LocationListener locationListener = new LocationListener() {

    @Override
    public void onLocationChanged(Location location) {
      showLocation(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
      checkEnabled();
    }

    @Override
    public void onProviderEnabled(String provider) {
      checkEnabled();
      showLocation(locationManager.getLastKnownLocation(provider));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
      if (provider.equals(LocationManager.GPS_PROVIDER)) {
        tvStatusGPS.setText("Status: " + String.valueOf(status));
      } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
        tvStatusNet.setText("Status: " + String.valueOf(status));
      }
    }
  };

  private void showLocation(Location location) {
    if (location == null)
      return;
    if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
      tvLocationGPS.setText(formatLocation(location));
    } else if (location.getProvider().equals(
        LocationManager.NETWORK_PROVIDER)) {
      tvLocationNet.setText(formatLocation(location));
    }
  }

  private String formatLocation(Location location) {
    if (location == null)
      return "";
    return String.format(
        "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
        location.getLatitude(), location.getLongitude(), new Date(
            location.getTime()));
  }

  private void checkEnabled() {
    tvEnabledGPS.setText("Enabled: "
        + locationManager
            .isProviderEnabled(LocationManager.GPS_PROVIDER));
    tvEnabledNet.setText("Enabled: "
        + locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER));
  }

  public void onClickLocationSettings(View view) {
    startActivity(new Intent(
        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
  }; */
    }

    @Override
    public void onResume() {
        super.onResume();
        checkNetwork();
        checkGPS();
    }

    @Override
    public void onPause() {
        super.onPause();
        checkNetwork();
        checkGPS();

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

    private void checkNetwork() {
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                setOnIndicatorNetwork();
            }
        else {
            setOffIndicatorNetwork();
        }

    }

    private void checkGPS() {
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            setOnIndicatorGPS();
        }
        else {
            setOffIndicatorGPS();
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

    private void setOnIndicatorGPS() {
        indicatorGpsOn.setVisibility(View.VISIBLE);
        indicatorGpsOff.setVisibility(View.INVISIBLE);
    }

    private void setOffIndicatorGPS() {
        indicatorGpsOff.setVisibility(View.VISIBLE);
        indicatorGpsOn.setVisibility(View.INVISIBLE);
    }
}
