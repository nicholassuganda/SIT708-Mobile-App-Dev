package com.tutorial.personalizedlearningexperienceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InterestsActivity extends AppCompatActivity {

    private RecyclerView interestsRecyclerView;
    private Button continueButton;
    private List<Interest> interests = new ArrayList<>();
    SharedPreferences mySharedPref;
    private static final String PREFS_NAME = "MY_PREF";
    private static final String INTERESTS_KEY = "INTERESTS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        interestsRecyclerView = findViewById(R.id.interestsRecyclerView);
        continueButton = findViewById(R.id.continueButton);
        mySharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Setup interests
        initializeInterests();

        interestsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        interestsRecyclerView.setAdapter(new InterestsAdapter(interests, this::onInterestSelected));

        continueButton.setOnClickListener(v -> saveSelectedInterests());
    }

    private void initializeInterests() {
        String[] interestNames = {
                "Algorithms", "Data Structures", "Web Development", "Testing",
                "Machine Learning", "Mobile Development", "Databases", "Security"
        };

        // Get previously selected interests
        Set<String> savedInterests = mySharedPref.getStringSet(INTERESTS_KEY, new HashSet<>());

        for (String name : interestNames) {
            interests.add(new Interest(name, savedInterests.contains(name)));
        }
    }

    private void onInterestSelected(int position, boolean isSelected) {
        interests.get(position).setSelected(isSelected);
    }

    private void saveSelectedInterests() {
        Set<String> selectedInterests = new HashSet<>();
        for (Interest interest : interests) {
            if (interest.isSelected()) {
                selectedInterests.add(interest.getName());
            }
        }

        if (selectedInterests.isEmpty()) {
            Toast.makeText(this, "Please select at least one interest", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save to SharedPreferences
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putStringSet(INTERESTS_KEY, selectedInterests);
        editor.apply();

        // Return to dashboard
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}