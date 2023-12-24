package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        // Initialize and setup bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                Intent intent1 = new Intent(MainActivity.this, HomeFragment.class);
                startActivity(intent1);
                // Handle Home icon click - Show Home Fragment
                // Replace fragment or perform action to display home content
                return true;
            } else if (itemId == R.id.nav_search) {
                // Handle Search icon click - Show Search Fragment
                // Replace fragment or perform action to display search content
                return true;
            } else if (itemId == R.id.nav_upload) {
                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(intent);
                return true;

            } else if (itemId == R.id.nav_profile) {
                Intent intent2 = new Intent(MainActivity.this, UserProfile.class);
                startActivity(intent2);
                return true;
            }
            return false;
        });

    }
}