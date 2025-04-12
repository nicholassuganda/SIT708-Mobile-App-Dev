package com.tutorial.taskmanagerapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private NavigationHandler navigationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize NavigationHandler
        navigationHandler = new NavigationHandler(
                getSupportFragmentManager(),
                R.id.fragment_container
        );

        // Set default fragment
        navigationHandler.setDefaultFragment(new TaskListFragment());

        // Setup bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        navigationHandler.setupWithBottomNavigation(bottomNav);
    }
}