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

import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.post.CityDataPostGenerator;

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

        GeneratorTask generatorTask = new GeneratorTask();
        generatorTask.execute();

        return root;
    }

    private void writeStartMessage() {
        consoleScreen.setText("- findView -- done");
        consoleBottomLine.setText("Start generate...");
        progressBar.setIndeterminate(true);
    }

    @SuppressLint("StaticFieldLeak")
    private class GeneratorTask extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setIndeterminate(true);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                CityDataPostGenerator generator = new CityDataPostGenerator();
                generator.generate();
            } catch (Exception e) {
                e.printStackTrace();
                consoleBottomLine.setText(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressBar.setIndeterminate(false);
        }
    }
}
