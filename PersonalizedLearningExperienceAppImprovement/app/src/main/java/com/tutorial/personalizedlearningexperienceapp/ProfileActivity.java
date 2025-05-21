package com.tutorial.personalizedlearningexperienceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {
    Button historyButton, upgradeButton, shareButton;
    private QuizDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dbHelper = new QuizDatabaseHelper(this);

        // Load profile data
        loadProfileData();

        historyButton = findViewById(R.id.historyButton);
        upgradeButton = findViewById(R.id.upgradeButton);
        shareButton = findViewById(R.id.shareButton);

        historyButton.setOnClickListener(v -> {
            startActivity(new Intent(this, HistoryActivity.class));
        });

        upgradeButton.setOnClickListener(v -> {
            startActivity(new Intent(this, UpgradeActivity.class));
        });

        shareButton.setOnClickListener(v -> shareProfile());
    }

    private void loadProfileData() {
        // Load from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("LearningAppPrefs", MODE_PRIVATE);
        String name = prefs.getString("username", "User");
        String email = prefs.getString("email", "user@example.com");

        // Load from database
        int totalQuizzes = dbHelper.getTotalQuizzes();
        float averageScore = dbHelper.getAverageScore();

        // Update UI
        TextView profileName = findViewById(R.id.profileName);
        TextView profileEmail = findViewById(R.id.profileEmail);
        TextView quizzesCompleted = findViewById(R.id.quizzesCompleted);
        TextView averageScoreText = findViewById(R.id.averageScore);

        profileName.setText(name);
        profileEmail.setText(email);
        quizzesCompleted.setText(String.valueOf(totalQuizzes));
        averageScoreText.setText(String.format(Locale.getDefault(), "%.1f%%", averageScore));
    }

    private void shareProfile() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out my learning progress!");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "I'm using Personalized Learning Experience App to improve my skills!");

        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}

