package com.example.hw8_5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MapsActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getSupportActionBar().hide();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavSelect);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavSelect = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
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
            };
}