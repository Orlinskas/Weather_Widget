package com.orlinskas.weatherwidget.forecast;

import java.util.ArrayList;

public class InstrumentPerformanceBuilder {
    private Forecast forecast;
    private final String PRESSURE = "pressure";
    private final String WIND_SPEED = "wind";
    private final String HUMIDITY = "humidity";
    private final String RAIN = "rain";
    private final String SNOW = "snow";

    public InstrumentPerformanceBuilder(Forecast forecast) {
        this.forecast = forecast;
    }

    private InstrumentPerformance build() {
        int pressure = (int) pickOutData(PRESSURE);
        double wind = pickOutData(WIND_SPEED);
        int humidity = (int) pickOutData(HUMIDITY);
        int rain = (int) pickOutData(RAIN);
        int snow = (int) pickOutData(SNOW);

        return new InstrumentPerformance(pressure, wind, humidity, rain, snow);
    }

    private double pickOutData(String param) {
        ArrayList<Double> values = new ArrayList<>();

        ArrayList<Weather> dayWeathers = forecast.getDayWeathers();

        for(Weather weather : dayWeathers) {
            switch (param) {
                case PRESSURE:
                    values.add((double) weather.getPressure());
                    break;
                case WIND_SPEED:
                    values.add(weather.getWindSpeed());
                    break;
                case HUMIDITY:
                    values.add((double)weather.getHumidity());
                    break;
                case RAIN:
                    values.add((double)weather.getRainVolume());
                    break;
                case SNOW:
                    values.add((double)weather.getSnowVolume());
                    break;
            }
        }

        return AveragerValue.average(values);
    }
}
