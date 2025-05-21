package com.tutorial.personalizedlearningexperienceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private RecyclerView resultsRecyclerView;
    private Button continueButton;
    private TextView resultsTitle, scoreTextView;
    private QuizDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        dbHelper = new QuizDatabaseHelper(this);

        // Initialize views
        resultsTitle = findViewById(R.id.resultsTitle);
        scoreTextView = findViewById(R.id.scoreTextView);
        resultsRecyclerView = findViewById(R.id.resultsRecyclerView);
        continueButton = findViewById(R.id.continueButton);

        // Get data from intent
        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 1);
        String topic = getIntent().getStringExtra("topic");

        // Save to database
        dbHelper.addQuizResult(topic, score, total);

        // Set title and score
        resultsTitle.setText(topic + " Quiz Results");
        scoreTextView.setText(String.format("You scored %d out of %d", score, total));

        // Create detailed results list
        List<ResultItem> resultItems = new ArrayList<>();
        resultItems.add(new ResultItem("Total Questions", String.valueOf(total)));
        resultItems.add(new ResultItem("Correct Answers", String.valueOf(score)));
        resultItems.add(new ResultItem("Percentage", calculatePercentage(score, total) + "%"));
        resultItems.add(new ResultItem("Performance", getPerformanceMessage(score, total)));

        // Setup RecyclerView
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsRecyclerView.setAdapter(new ResultsAdapter(resultItems));

        continueButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultsActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private String calculatePercentage(int score, int total) {
        if (total == 0) return "0";
        return String.format("%.1f", (score * 100.0) / total);
    }

    private String getPerformanceMessage(int score, int total) {
        double percentage = (score * 100.0) / total;
        if (percentage >= 80) return "Excellent!";
        if (percentage >= 60) return "Good job!";
        if (percentage >= 40) return "Not bad!";
        return "Keep practicing!";
    }
}