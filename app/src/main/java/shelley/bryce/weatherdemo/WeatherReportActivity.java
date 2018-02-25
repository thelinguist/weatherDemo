package shelley.bryce.weatherdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import shelley.bryce.weatherdemo.models.WeatherReport;
import shelley.bryce.weatherdemo.models.WeatherReportApp;

/**
 * Created by Bryce on 2/24/18.
 */

public class WeatherReportActivity extends AppCompatActivity {
    Toolbar detailToolbar;
    WeatherReport weatherReport;
    ImageView icon;
    TextView currentTemp;
    TextView lowTemp;
    TextView highTemp;
    TextView chancePrecip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        Intent intent = getIntent();
        if (intent != null) {
            weatherReport = WeatherReportApp.getInstance().getWeatherReport(intent.getStringExtra(Constants.CITY_ID));
            if (weatherReport != null) {
                bindViews();
                setValues();
            } else {
                //send them back to main screen
            }
        } else {
            //send them back to main screen
        }
    }

    /**
     * get the views and bind them to a variable
     */
    private void bindViews() {
        detailToolbar = (Toolbar) findViewById(R.id.report_toolbar);
        currentTemp = (TextView) findViewById(R.id.current_temp);
        lowTemp = (TextView) findViewById(R.id.current_low);
        highTemp = (TextView) findViewById(R.id.current_hi);
        chancePrecip = (TextView) findViewById(R.id.chance_precip);
        icon = (ImageView) findViewById(R.id.weather_report_icon);
    }

    /**
     * use the weatherReport to set the text and icon values
     */
    private void setValues() {
        detailToolbar.setTitle(weatherReport.getCityName());
        setSupportActionBar(detailToolbar);
        currentTemp.setText(String.valueOf(weatherReport.getCurrentTemp()) + "º");
        lowTemp.setText(String.valueOf(weatherReport.getLowTemp()) + "º");
        highTemp.setText(String.valueOf(weatherReport.getHighTemp()) + "º");
        chancePrecip.setText(String.valueOf(weatherReport.getPrecipChance()) + "%");
        icon.setImageBitmap(WeatherReportApp.getInstance().getIcon(weatherReport.getWeatherIcon()));
    }

}
