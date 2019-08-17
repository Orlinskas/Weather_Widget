package com.orlinskas.weatherwidget.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.orlinskas.weatherwidget.forecast.ForecastFiveDayRepositoryGetter;
import com.orlinskas.weatherwidget.forecast.ForecastReceiver;
import com.orlinskas.weatherwidget.forecast.WidgetUpdateChecker;
import com.orlinskas.weatherwidget.widget.Widget;
import com.orlinskas.weatherwidget.widget.WidgetRepository;

public class WidgetFragment extends Fragment implements WidgetObserver {
    private Widget widget;
    private TextView textView1, textView2;

    public WidgetFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_widget, container, false);
        getFragmentArgument();

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

    private boolean checkNeedUpdate() {
        WidgetUpdateChecker updateChecker = new WidgetUpdateChecker();
        return updateChecker.check(widget);
    }

    @Override
    public void update() {
        UpdateWidgetTask updateWidgetTask = new UpdateWidgetTask(getContext(), widget);
        updateWidgetTask.execute();
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    private void updateUI() { }

    @SuppressLint("StaticFieldLeak")
    private class UpdateWidgetTask extends AsyncTask<Void, Void, Void> {
        private Context context;
        private Widget widget;

        UpdateWidgetTask(Context context, Widget widget) {
            this.context = context;
            this.widget = widget;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                sendRequest();
                updateForecastInWidget();
                updateWidgetInRepository(widget);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setWidget(widget);
            updateUI();
        }

        private void sendRequest() {
            ForecastReceiver receiver = new ForecastReceiver(context, widget);
            receiver.receive();
        }

        private void updateForecastInWidget() {
            ForecastFiveDayRepositoryGetter fiveDayRepositoryGetter = new ForecastFiveDayRepositoryGetter(widget, context);
            widget.setForecastFiveDay(fiveDayRepositoryGetter.process());
        }

        private void updateWidgetInRepository(Widget widget) {
            try {
                WidgetRepository repository = new WidgetRepository(context);
                repository.update(widget);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}