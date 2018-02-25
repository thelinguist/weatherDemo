package shelley.bryce.weatherdemo.adapters;

import shelley.bryce.weatherdemo.Constants;
import shelley.bryce.weatherdemo.R;
import shelley.bryce.weatherdemo.WeatherReportActivity;
import shelley.bryce.weatherdemo.models.WeatherReport;
import shelley.bryce.weatherdemo.models.WeatherReportApp;
import shelley.bryce.weatherdemo.utils.FetchGraphic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Created by Bryce on 2/24/18.
 */

public class WeatherReportsAdapter extends RecyclerView.Adapter<WeatherReportsAdapter.WeatherReportHolder> {
    private ArrayList<WeatherReport> weatherReports;
    private Context context;

    public WeatherReportsAdapter(ArrayList<WeatherReport> weatherReports, Context context) {
        this.weatherReports = weatherReports;
        this.context = context;
    }
    public WeatherReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_city, parent, false);
        return new WeatherReportHolder(view, context);
    }

    public int getItemCount() {
        return weatherReports.size();
    }

    public void onBindViewHolder(WeatherReportHolder ph, int pos) {
        ph.bindWeatherReport(weatherReports.get(pos));
    }


    /**
     * the WeatherReportHolder that binds WeatherReports to a view in RecycleView
     */
    public class WeatherReportHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameView;
        private TextView tempView;
        private ImageView icon;
        private WeatherReport weatherReport;
        private Context mContext;

        public WeatherReportHolder(View view, Context context) {
            //comes from weatherReportAdapter?
            super(view);
            mContext = context;
            nameView = (TextView) view.findViewById(R.id.city_name_view);
            tempView = (TextView) view.findViewById(R.id.city_temp_view);
            icon = (ImageView) view.findViewById(R.id.weather_list_icon);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(mContext, WeatherReportActivity.class);
//            i.putExtra("weatherReport", weatherReport.getUid());
            i.putExtra(Constants.CITY_ID, weatherReport.getCityID());
            mContext.startActivity(i);
        }

        /**
         * connect data members to view items
         *
         * @param weatherReport a weatherReport to get details from
         */
        public void bindWeatherReport(final WeatherReport weatherReport) {
            this.weatherReport = weatherReport;
            nameView.setText(weatherReport.getCityName());
            tempView.setText(String.valueOf(weatherReport.getCurrentTemp()) + "º");


            if(WeatherReportApp.getInstance().getIcons().containsKey(weatherReport.getWeatherIcon())) {
                icon.setImageBitmap(WeatherReportApp.getInstance().getIcon(weatherReport.getWeatherIcon()));
            } else {
                FetchGraphic fetchGraphic = new FetchGraphic();
                fetchGraphic.setListener(new FetchGraphic.OnTaskCompleted() {
                    public void onTaskCompleted(Bitmap result) {
                        if (result != null) {
                            WeatherReportApp.getInstance().addIcon(weatherReport.getWeatherIcon(), result);
                            icon.setImageBitmap(result);
                        }
                    }
                });

                fetchGraphic.execute(weatherReport.getWeatherIcon());
                try {
                    fetchGraphic.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
