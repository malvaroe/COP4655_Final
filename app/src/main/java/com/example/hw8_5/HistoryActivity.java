package com.example.hw8_5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    WeatherData weatherData = MainActivity.getWeatherData();
    ListView listView;
    HistoryAdapter adapter;
    ArrayList<Day> arrayOfDays;

    private final String API_KEY = "6daef13389a6907d559375c171d944d9";

    private RequestQueue queue;
    private Context context;

    Day day1, day2, day3, day4, day5;
    double temp1, temp2, temp3, temp4, temp5;
    int dt1, dt2, dt3, dt4, dt5;
    String url1, url2, url3, url4, url5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().hide();

        context = getApplicationContext();

        queue = Volley.newRequestQueue(this);

        arrayOfDays = new ArrayList<Day>();
        adapter = new HistoryAdapter(this, arrayOfDays);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        String lat = weatherData.getLat();
        String lon = weatherData.getLon();
        int dt = Integer.parseInt(weatherData.getCurrentTime());

        dt1 = dt - (5 * 86400);
        dt2 = dt - (4 * 86400);
        dt3 = dt - (3 * 86400);
        dt4 = dt - (2 * 86400);
        dt5 = dt - (86400);

        String url1 = "https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + lat + "&lon=" + lon + "&dt=" + dt1 + "&appid=" + API_KEY;
        String url2 = "https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + lat + "&lon=" + lon + "&dt=" + dt2 + "&appid=" + API_KEY;
        String url3 = "https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + lat + "&lon=" + lon + "&dt=" + dt3 + "&appid=" + API_KEY;
        String url4 = "https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + lat + "&lon=" + lon + "&dt=" + dt4 + "&appid=" + API_KEY;
        String url5 = "https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + lat + "&lon=" + lon + "&dt=" + dt5 + "&appid=" + API_KEY;

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject current = response.getJSONObject("current");
                    Double temp1 = Math.floor(getTemp(current.getString("temp")));
                    day1 = new Day(1, temp1);
                    adapter.add(day1);
                } catch (JSONException e) {
                    Log.d("**", "JSON did not work");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("**", String.valueOf(error));
            }
        });
        queue.add(jsonObjectRequest1);
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject current = response.getJSONObject("current");
                    Double temp2 = Math.floor(getTemp(current.getString("temp")));
                    day2 = new Day(2, temp2);
                    adapter.add(day2);
                } catch (JSONException e) {
                    Log.d("**", "JSON did not work");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("**", String.valueOf(error));
            }
        });
        queue.add(jsonObjectRequest2);
        JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.GET, url3, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject current = response.getJSONObject("current");
                    Double temp3 = Math.floor(getTemp(current.getString("temp")));
                    day3 = new Day(3, temp3);
                    adapter.add(day3);
                } catch (JSONException e) {
                    Log.d("**", "JSON did not work");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("**", String.valueOf(error));
            }
        });
        queue.add(jsonObjectRequest3);
        JsonObjectRequest jsonObjectRequest4 = new JsonObjectRequest(Request.Method.GET, url4, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject current = response.getJSONObject("current");
                    Double temp4 = Math.floor(getTemp(current.getString("temp")));
                    day4 = new Day(4, temp4);
                    adapter.add(day4);
                } catch (JSONException e) {
                    Log.d("**", "JSON did not work");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("**", String.valueOf(error));
            }
        });
        queue.add(jsonObjectRequest4);
        JsonObjectRequest jsonObjectRequest5 = new JsonObjectRequest(Request.Method.GET, url5, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject current = response.getJSONObject("current");
                    Double temp5 = Math.floor(getTemp(current.getString("temp")));
                    day5 = new Day(5, temp5);
                    adapter.add(day5);
                } catch (JSONException e) {
                    Log.d("**", "JSON did not work");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("**", String.valueOf(error));
            }
        });
        queue.add(jsonObjectRequest5);




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
    public double getTemp(String t){
        double temp = Double.parseDouble(t);
        double tempF = (((temp - 273.15) * (1.8)) + 32);
        Log.d("**", String.valueOf(tempF));
        return tempF;
    }

}