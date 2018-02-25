package shelley.bryce.weatherdemo.utils;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import shelley.bryce.weatherdemo.models.WeatherReport;
import shelley.bryce.weatherdemo.models.WeatherReportApp;

/**
 * Created by Bryce on 2/25/18.
 */

public class FetchWeather extends AsyncTask<String, Void, String> {
    String baseURL = "http://api.openweathermap.org/data/2.5/";
    String apikey = "da65fafb6cb9242168b7724fb5ab75e7";
    String units = "imperial";
    private JSONObject JSONstuff;

    private OnTaskCompleted listener;


    public interface OnTaskCompleted{
        void onTaskCompleted();
    }

    public void setListener(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String urlString = baseURL + "group?id=";
        for (int i = 0; i < strings.length; i++) {
            urlString += strings[i];
            if (i != strings.length - 1) {
                urlString += ",";
            }
        }
        urlString += "&units=" + units;
        urlString += "&APPID=" + apikey;

//        Log.d("fetch", urlString);

        try {
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                return baos.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings[0];
    }

    protected void onPostExecute(String result){
        try {
            Log.d("fetch", result);
            JSONstuff = new JSONObject(result);
            parseJSON();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        listener.onTaskCompleted();
    }

    private void parseJSON() {
//        HashMap<String, WeatherReport> reports = new HashMap<>(); //TODO convert to Hashmap if dynamically adding more cities
        ArrayList<WeatherReport> reports = new ArrayList();
        try {
            for (int i = 0; i < JSONstuff.getJSONArray("list").length(); i++) {
                JSONObject report = JSONstuff.getJSONArray("list").getJSONObject(i);
//                JSONArray weather = report.getJSONArray("weather");
//                String weatherDescripton = weather.getJSONObject(0).getString("description");
//                Double low = report.getJSONObject("main").getDouble("temp_min");
//                Double hi = report.getJSONObject("main").getDouble("temp_max");
//                Double precip = report.getJSONObject("main").getDouble("humidity");

//                reports.put(report.getString("id"), new WeatherReport(
                reports.add(new WeatherReport(
                        report.getString("name"),
                        report.getString("id"),
                        report.getJSONArray("weather").getJSONObject(0).getString("icon"),
                        report.getJSONObject("main").getDouble("temp"),
                        report.getJSONObject("main").getDouble("temp_min"),
                        report.getJSONObject("main").getDouble("temp_max"),
                        report.getJSONObject("main").getDouble("humidity")));
            }
            WeatherReportApp.getInstance().setReports(reports);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //{
        // "cnt":3,
        // "list":[{
            //  "coord":{
        //          "lon":-111.89,"lat":40.76},
        //      "sys":{
        //          "type":1,
        //          "id":2802,"message":0.004,"country":"US","sunrise":1519567566,"sunset":1519607719},
        //      "weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}],
        //      "main":{"temp":271.32,"pressure":1023,"humidity":37,"temp_min":270.15,"temp_max":274.15},
        //      "visibility":16093,
        //      "wind":{"speed":2.6,"deg":90},
        //      "clouds":{"all":40},
        //      "dt":1519590846,
        //      "id":5780993,
        //      "name":"Salt Lake City"
        //  },{
        //      "coord":{
        //          "lon":-122.42,"lat":37.77},"sys":{"type":1,"id":478,"message":0.0056,"country":"US","sunrise":1519569919,"sunset":1519610417},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"main":{"temp":285.88,"pressure":1027,"humidity":58,"temp_min":284.15,"temp_max":288.15},"visibility":16093,"wind":{"speed":2.6,"deg":50},"clouds":{"all":1},"dt":1519590846,"id":5391959,"name":"San Francisco"},{"coord":{"lon":-74.01,"lat":40.71},"sys":{"type":1,"id":1969,"message":0.0052,"country":"US","sunrise":1519558482,"sunset":1519598624},"weather":[{"id":701,"main":"Mist","description":"mist","icon":"50d"},{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"main":{"temp":279.07,"pressure":1015,"humidity":93,"temp_min":278.15,"temp_max":280.15},"visibility":16093,"wind":{"speed":3.6,"deg":30},"clouds":{"all":90},"dt":1519590846,"id":5128581,"name":"New York"}]}

    }
}
