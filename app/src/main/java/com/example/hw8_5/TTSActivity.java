package com.example.hw8_5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TTSActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    TextView cityText, degText, feelsText, humidText, windText, cloudText, pressureText, riseText, setText, geoText;
    WeatherData weatherData = MainActivity.getWeatherData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_t_s);

        getSupportActionBar().hide();


        cityText = findViewById(R.id.cityText);
        degText = findViewById(R.id.degText);
        feelsText = findViewById(R.id.feelsText);
        humidText = findViewById(R.id.humidText);
        windText = findViewById(R.id.windText);
        cloudText = findViewById(R.id.cloudText);
        pressureText = findViewById(R.id.pressureText);
        riseText = findViewById(R.id.riseText);
        setText = findViewById(R.id.setText);
        geoText = findViewById(R.id.geoText);



        cityText.setText("Weather for " + weatherData.getCity());
        degText.setText(Math.floor(getTemp(weatherData.getTemp())) + " F");
        feelsText.setText(Math.floor(getTemp(weatherData.getFeelsLike())) + " F");
        humidText.setText(weatherData.getHumidity() + "%");
        windText.setText(weatherData.getWind() + " m/s");
        cloudText.setText(getCloudText());
        pressureText.setText(weatherData.getPressure() + " hpa");
        riseText.setText(getTime(weatherData.getSunrise()) + " EST");
        setText.setText(getTime(weatherData.getSunset()) + " EST");
        geoText.setText("[" + weatherData.getLat() + ", " + weatherData.getLon() + "]");

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavSelect);
    }


    public String getCloudText() {
        int cloudiness = Integer.parseInt(weatherData.getClouds());
        if (cloudiness < 11) {
            return "Sunny and clear";
        } else if (cloudiness < 21) {
            return "Fair";
        } else if (cloudiness < 31) {
            return "Mostly sunny";
        } else if (cloudiness < 61) {
            return "Partly cloudy";
        } else if (cloudiness < 71) {
            return "Partly sunny";
        } else if (cloudiness < 91) {
            return "Mostly cloudy";
        } else {
            return "Overcast";
        }
    }
    public String getTime(String timeInUnixTS) {
        Date d = new Date(Long.parseLong(timeInUnixTS) * 1000);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        return sdf.format(d);
    }

    public double getTemp(String t){
        double temp = Double.parseDouble(t);
        double tempF = (((temp - 273.15) * (1.8)) + 32);
        Log.d("**", String.valueOf(tempF));
        return tempF;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavSelect = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(homeIntent);
                            return true;
                        case R.id.action_tts:
                            Intent ttsIntent = new Intent(getApplicationContext(), TTSActivity.class);
                            startActivity(ttsIntent);
                            return true;
                        case R.id.action_map:

                            Intent mapIntent = new Intent(getApplicationContext(), MapsActivity.class);
                            startActivity(mapIntent);
                            return true;
                        case R.id.action_history:
                            Intent historyIntent = new Intent(getApplicationContext(), HistoryActivity.class);
                            startActivity(historyIntent);
                            return true;
                    }
                    return true;
                }
            };

}