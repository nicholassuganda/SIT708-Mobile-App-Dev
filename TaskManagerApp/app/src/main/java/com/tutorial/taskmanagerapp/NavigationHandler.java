package com.tutorial.taskmanagerapp;

// NavigationHandler.java
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationHandler {
    private final FragmentManager fragmentManager;
    private final int containerId;
    private BottomNavigationView.OnItemSelectedListener navListener;

    public NavigationHandler(FragmentManager fragmentManager, int containerId) {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
        setupNavigationListener();
    }

    private void setupNavigationListener() {
        navListener = item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_tasks) {
                selectedFragment = new TaskListFragment();
            } else if (itemId == R.id.nav_add) {
                selectedFragment = new AddEditTaskFragment();
            }

            if (selectedFragment != null) {
                fragmentManager.beginTransaction()
                        .replace(containerId, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        };
    }

    public void setupWithBottomNavigation(BottomNavigationView bottomNav) {
        bottomNav.setOnItemSelectedListener(navListener);
    }

    public void setDefaultFragment(Fragment defaultFragment) {
        fragmentManager.beginTransaction()
                .replace(containerId, defaultFragment)
                .commit();
    }
}
