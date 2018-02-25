package shelley.bryce.weatherdemo.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Bryce on 2/24/18. This class keeps all relevant data in memory between activities, currently just Weather Reports
 */

public class WeatherReportApp {
    private HashMap<String, WeatherReport> reports;
    private ArrayList<String> cityIDs;
    private static final String[] DEFAULT_CITY_IDS = {"5780993","5391959","5128581"};    //slc, san fran, new york
    private HashMap<String, Bitmap> icons;


    private static WeatherReportApp instance;

    //make the model a singleton to use in memory during the user's session.
    public static WeatherReportApp getInstance() {
        if (instance == null) {
            instance = new WeatherReportApp();
        }
        return instance;
    }

    private WeatherReportApp() {
        cityIDs = new ArrayList<>();
        cityIDs.addAll(Arrays.asList(DEFAULT_CITY_IDS));
        icons = new HashMap<>();
    }

    /**
     * add a city id to the list of cityIDs
     * @param id a city ID
     */
    public void addCityID(String id) {
        cityIDs.add(id);
    }

    /**
     * remove a city id to the list of cityIDs
     * @param id a city ID
     */
    public void removeCityID(String id) {
        cityIDs.remove(id);
    }

    public ArrayList<String> getCityIDs() {
        return cityIDs;
    }

    public HashMap<String, WeatherReport> getReports() {
        return reports;
    }

    public void setReports(HashMap<String, WeatherReport> reports) {
        this.reports = reports;
    }

    public WeatherReport getWeatherReport(String ID) {
        if(reports != null) {
            return reports.get(ID);
        }
        return null;
    }

    public HashMap<String, Bitmap> getIcons() {
        return icons;
    }

    public void addIcon(String id, Bitmap image) {
        icons.put(id, image);
    }

    public Bitmap getIcon(String id) {
        return icons.get(id);
    }
}
