package com.example.hw8_5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Button searchButton;
    private Button locButton;
    private ImageView speechButton;
    private EditText locText;

    public static WeatherData weatherData = new WeatherData();

    private final String API_KEY = "6daef13389a6907d559375c171d944d9";
    private final String apiURL = "https://api.openweathermap.org/data/2.5/weather?";
    private final String cityURL = apiURL + "q=";
    private final String zipURL = apiURL + "zip=";
    private final String latURL = "lat=";
    private final String lonURL = "&lon=";
    private final String keyURL = "&appid=" + API_KEY;

    private RequestQueue queue;
    private Context context;

    //Location Service permissions
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    //Creating a new GPS object
    GPS gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        context = getApplicationContext();

        queue = Volley.newRequestQueue(this);

        //Checks for permission
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchButton = (Button) findViewById(R.id.searchButton);
        locButton = (Button) findViewById(R.id.locButton);
        locText = (EditText) findViewById(R.id.locText);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(homeIntent);
                        break;
                    case R.id.action_tts:
                        Intent ttsIntent = new Intent(getApplicationContext(), TTSActivity.class);
                        startActivity(ttsIntent);
                        break;
                    case R.id.action_map:
                        System.out.println("** Entered bottomNavSelect->action_map");
                        Intent mapIntent = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(mapIntent);
                        break;
                    case R.id.action_history:
                        Intent historyIntent = new Intent(getApplicationContext(), HistoryActivity.class);
                        startActivity(historyIntent);
                        break;
                }
                return true;
            }
        });
    }

    public void onSearchButton(View view) {
        Log.d("**","Search Button Clicked");

        //Tries to parse an int, aka check if the submitted text is a zip code.
        try {
            int zip = Integer.parseInt(locText.getText().toString());
            getWeatherByZip(zip);
        }
        //If it gets a NaN exception or something like that, assume it's a city,state and execute that function.
        catch (Exception e) {
            String cityStateText = locText.getText().toString();
            getWeatherbyCity(cityStateText);
        }
    }

    //Listener for the location button
    public void onLocButton(View view) {
        gps = new GPS(MainActivity.this);
        //Checks if the location services are enabled and permitted
        if(gps.canGetLocation()){
            double lat = gps.getLatitude();
            double lon = gps.getLongitude();
            String URL = apiURL + latURL + lat + lonURL + lon + keyURL;
            Log.d("**", URL);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.d("**", "Response gained");
                        JSONObject main = response.getJSONObject("main");
                        JSONObject coord = response.getJSONObject("coord");
                        JSONObject wind = response.getJSONObject("wind");
                        JSONObject sys = response.getJSONObject("sys");
                        JSONObject clouds = response.getJSONObject("clouds");

                        weatherData.setCity(response.getString("name"));
                        weatherData.setTemp(main.getString("temp"));
                        weatherData.setFeelsLike(main.getString("feels_like"));
                        weatherData.setHumidity(main.getString("humidity"));
                        weatherData.setWind(wind.getString("speed"));
                        weatherData.setClouds(clouds.getString("all"));
                        weatherData.setPressure(main.getString("pressure"));
                        weatherData.setSunrise(sys.getString("sunrise"));
                        weatherData.setSunset(sys.getString("sunset"));
                        weatherData.setLat(coord.getString("lat"));
                        weatherData.setLon(coord.getString("lon"));
                        weatherData.setCurrentTime(response.getString("dt"));

                        Intent goIntent = new Intent(context, TTSActivity.class);
                        startActivity(goIntent);
                    }
                    catch (JSONException e) {
                        Log.d("**", "JSON did not work");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("**", String.valueOf(error));
                }
            });
            queue.add(jsonObjectRequest);

        } else {
            gps.showSettingsAlert();
        }

    }
    public void getWeatherByZip(int zip) {
        Log.d("**", "getWeatherByZip invoked");
        String zipcode = String.valueOf(zip);
        String URL = zipURL + zipcode + keyURL;
        Log.d("**", URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                Log.d("**", "Response gained");
                JSONObject main = response.getJSONObject("main");
                JSONObject coord = response.getJSONObject("coord");
                JSONObject wind = response.getJSONObject("wind");
                JSONObject sys = response.getJSONObject("sys");
                JSONObject clouds = response.getJSONObject("clouds");

                weatherData.setCity(response.getString("name"));
                weatherData.setTemp(main.getString("temp"));
                weatherData.setFeelsLike(main.getString("feels_like"));
                weatherData.setHumidity(main.getString("humidity"));
                weatherData.setWind(wind.getString("speed"));
                weatherData.setClouds(clouds.getString("all"));
                weatherData.setPressure(main.getString("pressure"));
                weatherData.setSunrise(sys.getString("sunrise"));
                weatherData.setSunset(sys.getString("sunset"));
                weatherData.setLat(coord.getString("lat"));
                weatherData.setLon(coord.getString("lon"));
                weatherData.setCurrentTime(response.getString("dt"));

                Intent goIntent = new Intent(context, TTSActivity.class);
                startActivity(goIntent);
            }
            catch (JSONException e) {
                Log.d("**", "JSON did not work");
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("**", String.valueOf(error));
        }
    });
        queue.add(jsonObjectRequest);
}
    public void getWeatherbyCity(String cityState) {
        String URL = cityURL + cityState + ",US" + keyURL;
        Log.d("**", URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("**", "Response gained");
                    JSONObject main = response.getJSONObject("main");
                    JSONObject coord = response.getJSONObject("coord");
                    JSONObject wind = response.getJSONObject("wind");
                    JSONObject sys = response.getJSONObject("sys");
                    JSONObject clouds = response.getJSONObject("clouds");

                    weatherData.setCity(response.getString("name"));
                    weatherData.setTemp(main.getString("temp"));
                    weatherData.setFeelsLike(main.getString("feels_like"));
                    weatherData.setHumidity(main.getString("humidity"));
                    weatherData.setWind(wind.getString("speed"));
                    weatherData.setClouds(clouds.getString("all"));
                    weatherData.setPressure(main.getString("pressure"));
                    weatherData.setSunrise(sys.getString("sunrise"));
                    weatherData.setSunset(sys.getString("sunset"));
                    weatherData.setLat(coord.getString("lat"));
                    weatherData.setLon(coord.getString("lon"));
                    weatherData.setCurrentTime(response.getString("dt"));

                    Intent goIntent = new Intent(context, TTSActivity.class);
                    startActivity(goIntent);
                }
                catch (JSONException e) {
                    Log.d("**", "JSON did not work");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("**", String.valueOf(error));
            }
        });
        queue.add(jsonObjectRequest);
    }

    public static WeatherData getWeatherData(){
        return weatherData;
    }









}