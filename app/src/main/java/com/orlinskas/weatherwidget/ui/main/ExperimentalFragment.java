package com.orlinskas.weatherwidget.ui.main;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
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
import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.data.CitiesDatabase;
import com.orlinskas.weatherwidget.data.CitiesDatabaseAdapter;
import com.orlinskas.weatherwidget.json.JSONInputSteamCreator;
import com.orlinskas.weatherwidget.json.JSONLineParser;
import com.orlinskas.weatherwidget.json.JSONSteamReader;
import com.orlinskas.weatherwidget.post.CityDataJsonParser;
import com.orlinskas.weatherwidget.post.CityDataRepositoryWriter;

import java.util.ArrayList;


public class ExperimentalFragment extends Fragment {
    private Button button;
    private ProgressBar progressBar;
    private TextView consoleScreen;
    private TextView consoleBottomLine;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_city_data_generator, container, false);
        progressBar = root.findViewById(R.id.fragment_city_data_generator_pb);
        consoleScreen = root.findViewById(R.id.fragment_city_data_generator_tv_console);
        consoleBottomLine = root.findViewById(R.id.fragment_city_data_generator_tv_console_bottom);

        writeStartMessage();

        long test = 0;

        CitiesDatabaseAdapter adapter = new CitiesDatabaseAdapter(getContext());
        adapter.createDatabase();
        adapter.openWithTransaction();
        test = adapter.getCount(CitiesDatabase.TABLE_CITY);
        adapter.closeWithTransaction();

        return root;
    }

    private void writeStartMessage() {
        consoleScreen.setText("- findView -- done");
        consoleBottomLine.setText("Start parseJSONFile...");
        progressBar.setIndeterminate(true);
    }
}
