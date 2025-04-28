package com.tutorial.personalizedlearningexperienceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private TextView welcomeTextView, taskCountTextView;
    private RecyclerView tasksRecyclerView;
    private Button exploreButton;
    private List<String> userInterests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Get username with null check
        String userName = getIntent().getStringExtra("USER_NAME");
        if (userName == null) {
            userName = "Student"; // Default value
        }

        userInterests = getIntent().getStringArrayListExtra("interests");
        if (userInterests == null) {
            userInterests = Arrays.asList("Algorithms", "Data Structures"); // Default interests
        }

        welcomeTextView = findViewById(R.id.welcomeTextView);
        taskCountTextView = findViewById(R.id.taskCountTextView);
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        exploreButton = findViewById(R.id.exploreButton);

        welcomeTextView.setText("Hello, " + userName + " 😊");
        taskCountTextView.setText("You have " + userInterests.size() + " tasks due");

        // Setup tasks recycler view based on user interests
        List<Task> tasks = new ArrayList<>();
        for (String interest : userInterests) {
            tasks.add(new Task("Learn about " + interest,
                    "Complete quiz on " + interest,
                    true,
                    interest));
        }

        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        TaskAdapter adapter = new TaskAdapter(tasks, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(Task task) {
                // Generate quiz for this task's topic
                Intent intent = new Intent(DashboardActivity.this, QuizActivity.class);
                intent.putExtra("topic", task.getTopic());
                startActivity(intent);
            }
        });
        tasksRecyclerView.setAdapter(adapter);

        exploreButton.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, InterestsActivity.class));
        });
    }
}