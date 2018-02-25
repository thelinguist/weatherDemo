package shelley.bryce.weatherdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import shelley.bryce.weatherdemo.adapters.WeatherReportsAdapter;
import shelley.bryce.weatherdemo.models.WeatherReport;
import shelley.bryce.weatherdemo.models.WeatherReportApp;
import shelley.bryce.weatherdemo.utils.FetchWeather;

public class MainActivity extends AppCompatActivity {
    private RecyclerView citiesListView;
    private Button addCityButton;
    private WeatherReportsAdapter reportsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        citiesListView = (RecyclerView) findViewById(R.id.citiesList);

        FetchWeather fetchWeather = new FetchWeather();
        fetchWeather.setListener(new FetchWeather.OnTaskCompleted() {
            public void onTaskCompleted() {
//                WeatherReportApp.getInstance().setReports(getTestData());


                ArrayList<WeatherReport> reportsAsArray = new ArrayList<>();
                reportsAsArray.addAll(WeatherReportApp.getInstance().getReports().values());
                reportsAdapter = new WeatherReportsAdapter(reportsAsArray, getApplicationContext());
//        reportsAdapter = new WeatherReportsAdapter(WeatherReportApp.getInstance().getReports(), getApplicationContext());
                citiesListView.setAdapter(reportsAdapter);
                final LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                citiesListView.setLayoutManager(manager);
            }
        });

        fetchWeather.execute(WeatherReportApp.getInstance().getCityIDs().toArray(new String[WeatherReportApp.getInstance().getCityIDs().size()]));
        try {
            fetchWeather.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        //addCityButton = (Button) findViewById(R.id.addCityButton); TODO implement this for extra points
    }

    /**
     * Test with this data until fetchWeather is done
     * @return
     */
    private ArrayList<WeatherReport> getTestData() {
        ArrayList<WeatherReport> returnMe = new ArrayList<>();
        returnMe.add(new WeatherReport("Salt Lake", "5780993", null, 58.0, 40.0, 60.0, 0.01));
        returnMe.add(new WeatherReport("San Francisco", "5391959", null, -21.0, 40.0, 60.0, 0.01));
        return returnMe;
    }

    //TODO: no extra points :(
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_weather, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
