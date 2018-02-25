package shelley.bryce.weatherdemo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Bryce on 2/25/18.
 */

public class FetchGraphic extends AsyncTask<String, Void, Bitmap> {
    String baseURL = "http://openweathermap.org/img/w/";
    private FetchGraphic.OnTaskCompleted listener;


    public interface OnTaskCompleted{
        void onTaskCompleted(Bitmap result);
    }

    public void setListener(FetchGraphic.OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String urlString = baseURL + strings[0] + ".png";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    protected void onPostExecute(Bitmap result){
        listener.onTaskCompleted(result);
    }


}
