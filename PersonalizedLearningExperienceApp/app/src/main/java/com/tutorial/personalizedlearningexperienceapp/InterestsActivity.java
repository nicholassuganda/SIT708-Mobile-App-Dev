package com.tutorial.personalizedlearningexperienceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InterestsActivity extends AppCompatActivity {

    private RecyclerView interestsRecyclerView;
    private Button continueButton;
    private List<Interest> interests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        // Get username from intent at start
        String userName = getIntent().getStringExtra("USER_NAME");
        if (userName == null) {
            userName = "Student"; // Default if somehow null
        }

        interestsRecyclerView = findViewById(R.id.interestsRecyclerView);
        continueButton = findViewById(R.id.continueButton);

        // Setup interests
        interests.add(new Interest("Algorithms", false));
        interests.add(new Interest("Data Structures", false));
        interests.add(new Interest("Web Development", false));
        interests.add(new Interest("Testing", false));
        interests.add(new Interest("Machine Learning", false));
        interests.add(new Interest("Mobile Development", false));
        interests.add(new Interest("Databases", false));
        interests.add(new Interest("Security", false));

        interestsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        interestsRecyclerView.setAdapter(new InterestsAdapter(interests));

        String finalUserName = userName;
        continueButton.setOnClickListener(v -> {
            // Get selected interests
            List<String> selectedInterests = new ArrayList<>();
            for (Interest interest : interests) {
                if (interest.isSelected()) {
                    selectedInterests.add(interest.getName());
                }
            }

            if (selectedInterests.isEmpty()) {
                Toast.makeText(this, "Please select at least one interest", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedInterests.size() > 10) {
                Toast.makeText(this, "You can select up to 10 topics", Toast.LENGTH_SHORT).show();
                return;
            }

            // Return to dashboard with selected interests
            Intent intent = new Intent(InterestsActivity.this, DashboardActivity.class);
            intent.putStringArrayListExtra("interests", new ArrayList<>(selectedInterests));
            intent.putExtra("USER_NAME", finalUserName); // Make sure to pass the username back
            startActivity(intent);
            finish();
        });
    }
}