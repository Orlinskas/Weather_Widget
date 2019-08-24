package com.orlinskas.weatherwidget.forecast;

public class InstrumentPerformance {
    private int averagePressure;
    private double averageWindSpeed;
    private int averageHumidity;
    private int averageRainVolume;
    private int averageSnowVolume;

    public InstrumentPerformance(int averagePressure, double averageWindSpeed, int averageHumidity, int averageRainVolume, int averageSnowVolume) {
        this.averagePressure = averagePressure;
        this.averageWindSpeed = averageWindSpeed;
        this.averageHumidity = averageHumidity;
        this.averageRainVolume = averageRainVolume;
        this.averageSnowVolume = averageSnowVolume;
    }

    public int getAveragePressure() {
        return averagePressure;
    }

    public double getAverageWindSpeed() {
        return averageWindSpeed;
    }

    public int getAverageHumidity() {
        return averageHumidity;
    }

    public int getAverageRainVolume() {
        return averageRainVolume;
    }

    public int getAverageSnowVolume() {
        return averageSnowVolume;
    }
}
