package shelley.bryce.weatherdemo.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import shelley.bryce.weatherdemo.adapters.WeatherReportsAdapter;
import shelley.bryce.weatherdemo.utils.FetchGraphic;

/**
 * Created by Bryce on 2/24/18. This class keeps all relevant data in memory between activities, currently just Weather Reports
 */

public class WeatherReportApp {
    private ArrayList<WeatherReport> reports;   //TODO: hashmap with {ID, report}
    private ArrayList<String> cityIDs;
    private static final String[] DEFAULT_CITY_IDS = {"5780993","5391959","5128581"};    //slc, san fran, new york
    private HashMap<String, Bitmap> icons;


    private static WeatherReportApp instance;

    //make the model a singleton to use during the user's session.
    public static WeatherReportApp getInstance() {
        if (instance == null) {
            instance = new WeatherReportApp();
        }
        return instance;
    }

    WeatherReportApp() {
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

    }

    public ArrayList<String> getCityIDs() {
        return cityIDs;
    }

    public ArrayList<WeatherReport> getReports() {
        return reports;
    }

    public void setReports(ArrayList<WeatherReport> reports) {
        this.reports = reports;
    }

    public WeatherReport getWeatherReport(String ID) {
        if(reports != null) {
            for(int i = 0; i < reports.size(); i++) {
                if (reports.get(i).getCityID().equals(ID)) {
                    return reports.get(i);
                }
            }
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
