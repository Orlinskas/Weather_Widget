package com.orlinskas.weatherwidget.forecast;

import java.io.Serializable;
import java.util.Objects;

public class Weather implements Serializable {
    private int cityID;
    private String cityName;
    private String countryCode;
    private String timeOfDataForecast;
    private String forecastDate;
    private int currentTemperature;
    private int pressure;
    private int timezone;
    private int weatherID;
    private String weatherGroup;
    private String weatherGroupDescription;
    private String weatherIconID;
    private int cloudinessPercent;
    private double windSpeed;
    private int rainVolume;
    private int snowVolume;

    public Weather(String timeOfDataForecast) {
        this.timeOfDataForecast = timeOfDataForecast;
    }

    public Weather(int cityID, String cityName, String countryCode, String timeOfDataForecast,
                   String forecastDate, int currentTemperature, int pressure, int timezone,
                   int weatherID, String weatherGroup, String weatherGroupDescription, String weatherIconID,
                   int cloudinessPercent, double windSpeed, int rainVolume, int snowVolume) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.timeOfDataForecast = timeOfDataForecast;
        this.forecastDate = forecastDate;
        this.currentTemperature = currentTemperature;
        this.pressure = pressure;
        this.timezone = timezone;
        this.weatherID = weatherID;
        this.weatherGroup = weatherGroup;
        this.weatherGroupDescription = weatherGroupDescription;
        this.weatherIconID = weatherIconID;
        this.cloudinessPercent = cloudinessPercent;
        this.windSpeed = windSpeed;
        this.rainVolume = rainVolume;
        this.snowVolume = snowVolume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weather)) return false;
        Weather that = (Weather) o;
        return getCityID() == that.getCityID() &&
                getCurrentTemperature() == that.getCurrentTemperature() &&
                getPressure() == that.getPressure() &&
                getTimezone() == that.getTimezone() &&
                getWeatherID() == that.getWeatherID() &&
                getCloudinessPercent() == that.getCloudinessPercent() &&
                Double.compare(that.getWindSpeed(), getWindSpeed()) == 0 &&
                getRainVolume() == that.getRainVolume() &&
                getSnowVolume() == that.getSnowVolume() &&
                Objects.equals(getCityName(), that.getCityName()) &&
                getCountryCode().equals(that.getCountryCode()) &&
                getTimeOfDataForecast().equals(that.getTimeOfDataForecast()) &&
                getForecastDate().equals(that.getForecastDate()) &&
                Objects.equals(getWeatherGroup(), that.getWeatherGroup()) &&
                Objects.equals(getWeatherGroupDescription(), that.getWeatherGroupDescription()) &&
                Objects.equals(getWeatherIconID(), that.getWeatherIconID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCityID(), getCityName(), getCountryCode(), getTimeOfDataForecast(),
                getForecastDate(), getCurrentTemperature(), getPressure(), getTimezone(), getWeatherID(),
                getWeatherGroup(), getWeatherGroupDescription(), getWeatherIconID(), getCloudinessPercent(),
                getWindSpeed(), getRainVolume(), getSnowVolume());
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTimeOfDataForecast() {
        return timeOfDataForecast;
    }

    public void setTimeOfDataForecast(String timeOfDataForecast) {
        this.timeOfDataForecast = timeOfDataForecast;
    }

    public String getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(String forecastDate) {
        this.forecastDate = forecastDate;
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(int currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public int getWeatherID() {
        return weatherID;
    }

    public void setWeatherID(int weatherID) {
        this.weatherID = weatherID;
    }

    public String getWeatherGroup() {
        return weatherGroup;
    }

    public void setWeatherGroup(String weatherGroup) {
        this.weatherGroup = weatherGroup;
    }

    public String getWeatherGroupDescription() {
        return weatherGroupDescription;
    }

    public void setWeatherGroupDescription(String weatherGroupDescription) {
        this.weatherGroupDescription = weatherGroupDescription;
    }

    public String getWeatherIconID() {
        return weatherIconID;
    }

    public void setWeatherIconID(String weatherIconID) {
        this.weatherIconID = weatherIconID;
    }

    public int getCloudinessPercent() {
        return cloudinessPercent;
    }

    public void setCloudinessPercent(int cloudinessPercent) {
        this.cloudinessPercent = cloudinessPercent;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getRainVolume() {
        return rainVolume;
    }

    public void setRainVolume(int rainVolume) {
        this.rainVolume = rainVolume;
    }

    public int getSnowVolume() {
        return snowVolume;
    }

    public void setSnowVolume(int snowVolume) {
        this.snowVolume = snowVolume;
    }


}
