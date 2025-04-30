package com.tutorial.personalizedlearningexperienceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DashboardActivity extends AppCompatActivity {

    private TextView welcomeTextView, taskCountTextView;
    private RecyclerView tasksRecyclerView;
    private Button updateButton;
    private List<String> userInterests;
    String userName = "";
    SharedPreferences mySharedPref;
    private static final String PREFS_NAME = "MY_PREF";
    private static final String NAME_KEY = "USERNAME";
    private static final String INTERESTS_KEY = "INTERESTS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mySharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        userName = mySharedPref.getString(NAME_KEY,"Student");

        userInterests = getIntent().getStringArrayListExtra("interests");
        if (userInterests == null) {
            userInterests = Arrays.asList("Algorithms", "Data Structures"); // Default interests
        }

        welcomeTextView = findViewById(R.id.welcomeTextView);
        taskCountTextView = findViewById(R.id.taskCountTextView);
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        updateButton = findViewById(R.id.updateButton);

        welcomeTextView.setText("Hello, " + userName + " 😊");
        taskCountTextView.setText("You have " + userInterests.size() + " tasks due");

        // Set up update interests button
        updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, InterestsActivity.class);
            startActivity(intent);
        });

        // Load selected interests
        loadSelectedInterests();
    }

    private void loadSelectedInterests() {
        Set<String> selectedInterests = mySharedPref.getStringSet(INTERESTS_KEY, null);

        // If no interests selected, use default values
        if (selectedInterests == null || selectedInterests.isEmpty()) {
            selectedInterests = new HashSet<>(Arrays.asList("Algorithms", "Data Structures"));

            // Save the default interests
            SharedPreferences.Editor editor = mySharedPref.edit();
            editor.putStringSet(INTERESTS_KEY, selectedInterests);
            editor.apply();
        }

        // Create tasks based on selected interests
        List<Task> tasks = new ArrayList<>();
        for (String interest : selectedInterests) {
            tasks.add(new Task("Learn about " + interest,
                    "Complete quiz on " + interest,
                    true,
                    interest));
        }

        taskCountTextView.setText("You have " + tasks.size() + " learning tasks");
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerView.setAdapter(new TaskAdapter(tasks, task -> {
            // Handle task click
            Intent intent = new Intent(DashboardActivity.this, QuizActivity.class);
            intent.putExtra("topic", task.getTopic());
            startActivity(intent);
        }));
        tasksRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh tasks when returning from other activities
        loadSelectedInterests();
    }
}