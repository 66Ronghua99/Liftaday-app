package com.example.liftaday;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.liftaday.utils.Common;
import com.example.liftaday.utils.FileUtil;
import com.example.liftaday.utils.Helper;
import com.example.liftaday.utils.forecast.Forecast;
import com.example.liftaday.utils.news.NewsList;
import com.example.liftaday.utils.weather.OpenWeatherMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.zip.Inflater;


public class SplashScreen extends AppCompatActivity implements LocationListener {

    private static int SPLASH_TIME_OUT = 2000;


    double lat, lon;
    LocationManager locationManager;
    String provider;
    int MY_PERMISSION = 0;
    List<String> providerList;
    static String city, date, temp, weather,
            wind, humidity, tempToday, weatherImage,t;
    public static String name,hour,minutes;
    //when it's on the phone, the change of one static array would reset all other arrays also
    static String[] tempForecast, imageForecast, dayForecast,
            title, description, url, content, imageUrl,personalInfo;

    OpenWeatherMap openWeatherMap = new OpenWeatherMap();
    Forecast forecast = new Forecast();
    NewsList newsList = new NewsList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //In a activity, everything should be done after setContentView
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        TextView textView = findViewById(R.id.welcome);
        File file = new File(Environment.getExternalStorageDirectory(), "username.txt");
        String b = "";
        if(file.exists()){
            if(FileUtil.getFile(Setting.USERNAME)!=null){
                personalInfo = FileUtil.getFile(Setting.USERNAME).split("\n");
                name = personalInfo[0];
                hour = personalInfo[1];
                minutes = personalInfo[2];
                String a = getResources().getString(R.string.Name);
                Log.i("tag",a);
                b = String.format(a,name);
                Log.i("tag",b);
            }else {
                Log.i("tag","file is empty");
                b = "Welcome! New user! Kick start your day!";
            }
        }else {
            Log.i("tag","file not exists");
            b = "Welcome! New user! Kick start your day!";
        }
        textView.setText(b);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        providerList = locationManager.getProviders(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,

            }, MY_PERMISSION);
        }

//        if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
//            provider = LocationManager.NETWORK_PROVIDER;
//            Log.e("TAG1", "network");
//        }else
        if(providerList.contains(LocationManager.GPS_PROVIDER)){
            provider = LocationManager.GPS_PROVIDER;
            Log.e("TAG1", "gps");
        }else {
                Intent i = new Intent();
                i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null)
            Log.e("TAG1", "No Location");
        new GetWeather().execute(Common.apiRequestWeather(String.valueOf(lat), String.valueOf(lon)));
        new GetForecast().execute(Common.apiRequestForecast(String.valueOf(lat), String.valueOf(lon)));
        new GetNews().execute(Common.apiRequestNews());


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainActivity = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();

        new GetWeather().execute(Common.apiRequestWeather(String.valueOf(lat), String.valueOf(lon)));
        new GetForecast().execute(Common.apiRequestForecast(String.valueOf(lat), String.valueOf(lon)));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    private class GetWeather extends AsyncTask<String, Void, String> {

        ProgressDialog pd = new ProgressDialog(SplashScreen.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
//            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stream;
            String urlString = strings[0];
            Helper http = new Helper();
            stream = http.getHttpData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("Error: Not Found City")) {
                pd.dismiss();
            }
            Gson gson = new Gson();
            Type mType = new TypeToken<OpenWeatherMap>(){}.getType();
            openWeatherMap = gson.fromJson(s, mType);
            pd.dismiss();

            city = String.format("%s, %s", openWeatherMap.getName(), openWeatherMap.getSys().getCountry());
            date = String.format("%s", Common.getDate());
            weather = String.format("%s", openWeatherMap.getWeather().get(0).getDescription());
            humidity = String.format("Humidity: %s%%", openWeatherMap.getMain().getHumidity());
            temp = String.format("%s°C", openWeatherMap.getMain().getTemp());
            wind = String.format("Wind speed: %s m/s", openWeatherMap.getWind().getSpeed());
            tempToday = String.format("Temperature: %s°C / %s°C", openWeatherMap.getMain().getTemp_max(), openWeatherMap.getMain().getTemp_min());
            weatherImage = Common.getImage(openWeatherMap.getWeather().get(0).getIcon());
            t = String.format("%s",openWeatherMap.getMain().getTemp());
        }
    }

    private class GetForecast extends AsyncTask<String, Void, String> {

        ProgressDialog pd = new ProgressDialog(SplashScreen.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String stream;
            String urlString = strings[0];
            Helper http = new Helper();
            stream = http.getHttpData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("Error: Not Found City")) {
                pd.dismiss();
            }
            Gson gson = new Gson();
            Type mType = new TypeToken<Forecast>() {
            }.getType();
            forecast = gson.fromJson(s, mType);
            pd.dismiss();

            dayForecast = new String[5];
            tempForecast = new String[5];
            imageForecast = new String[5];

            for (int i = 0; i < 5; i++) {
                dayForecast[i] = getDay(i);
                tempForecast[i] = String.format("%s°C", forecast.getList().get(8 * i + 7).getMain().getTemp());
                imageForecast[i] = Common.getImage(forecast.getList().get(8 * i + 7).getWeather().get(0).getIcon());
            }
        }
    }

    private String getDay(int i) {

        Date dateToday = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dateToday);
        calendar.add(calendar.DATE, i + 1);
        dateToday = calendar.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        String dayToday = formatter.format(dateToday);

        return dayToday;
    }

    private class GetNews extends AsyncTask<String,Void,String>{

        ProgressDialog pd = new ProgressDialog(SplashScreen.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String stream;
            String urlString = strings[0];
            Helper http = new Helper();
            stream = http.getHttpData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("Error: No news found")) {
                pd.dismiss();
            }
            Gson gson = new Gson();
            Type mType = new TypeToken<NewsList>() {
            }.getType();
            newsList = gson.fromJson(s, mType);
            pd.dismiss();
            title = new String[3];
            description = new String[3];
            url = new String[3];
            content = new String[3];
            imageUrl = new String[3];
            for(int i=0;i<3;i++){
                title[i] = newsList.getArticle().get(i).getTitle();
                description[i] = newsList.getArticle().get(i).getDescription();
                content[i] = newsList.getArticle().get(i).getContent();
                url[i] = newsList.getArticle().get(i).getUrl();
                imageUrl[i] = newsList.getArticle().get(i).getUrlToImage();
                if(content[i]!=null){
                    int n = content[i].indexOf("\n");
                    content[i] = content[i].substring(n,content[i].length()-13)+" \n\nClick to see more details on BBC News";
                }
            }
        }
    }


}

