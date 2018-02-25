package shelley.bryce.weatherdemo.models;

/**
 * Created by Bryce on 2/24/18.
 */

public class WeatherReport {
    private String cityName;
    private String cityID;
    private String weatherIcon;
    private Double currentTemp;
    private Double lowTemp;
    private Double highTemp;
    private Double precipChance;

    public WeatherReport(String cityName, String cityID, String weatherIcon, Double currentTemp, Double lowTemp, Double highTemp, Double precipChance) {
        this.cityName = cityName;
        this.cityID = cityID;
        this.weatherIcon = weatherIcon;
        this.currentTemp = currentTemp;
        this.lowTemp = lowTemp;
        this.highTemp = highTemp;
        this.precipChance = precipChance;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public Double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(Double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public Double getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(Double lowTemp) {
        this.lowTemp = lowTemp;
    }

    public Double getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(Double highTemp) {
        this.highTemp = highTemp;
    }

    public Double getPrecipChance() {
        return precipChance;
    }

    public void setPrecipChance(Double precipChance) {
        this.precipChance = precipChance;
    }
}
