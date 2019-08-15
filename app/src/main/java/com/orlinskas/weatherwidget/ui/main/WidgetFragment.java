package com.orlinskas.weatherwidget.ui.main;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.orlinskas.weatherwidget.R;
import com.orlinskas.weatherwidget.ToastBuilder;
import com.orlinskas.weatherwidget.forecast.ForecastFiveDay;
import com.orlinskas.weatherwidget.forecast.ForecastFiveDayRepositoryGetter;
import com.orlinskas.weatherwidget.forecast.ForecastReceiver;
import com.orlinskas.weatherwidget.forecast.ForecastUpdateChecker;
import com.orlinskas.weatherwidget.widget.Widget;

import java.text.ParseException;

public class WidgetFragment extends Fragment implements WidgetObserver {
    private Widget widget;
    private TextView textView1, textView2;
    private ForecastFiveDay forecastFiveDay;

    public WidgetFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_widget, container, false);
        getFragmentArgument();

        textView1 = root.findViewById(R.id.textView);
        textView2 = root.findViewById(R.id.textView2);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(checkNeedUpdate()){
            update();
        }
    }

    private void getFragmentArgument() {
        if (getArguments() != null) {
            widget = (Widget) getArguments().getSerializable("widget");
        }
    }

    @Override
    public void update() {
        UpdateTask updateTask = new UpdateTask();
        updateTask.execute();
    }

    private void getForecastFromRepository() {
        ForecastFiveDayRepositoryGetter fiveDayRepositoryGetter = new ForecastFiveDayRepositoryGetter(widget, getContext());
        try {
            forecastFiveDay = fiveDayRepositoryGetter.get();
        } catch (ParseException e) {
            e.printStackTrace();
            ToastBuilder.create(getContext(),"Нет данных");
        }
    }

    private boolean checkNeedUpdate() {
        ForecastUpdateChecker updateChecker = new ForecastUpdateChecker();
        return updateChecker.check(forecastFiveDay);
    }

    private void sendRequest() {
        ForecastReceiver receiver = new ForecastReceiver(getContext(),widget);
        receiver.receive();
    }

    private void updateUI() {

    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                sendRequest();
                getForecastFromRepository();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateUI();
        }
    }
}