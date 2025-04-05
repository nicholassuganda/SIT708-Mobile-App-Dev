package com.tutorial.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView congratsTextView = findViewById(R.id.congratsTextView);
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        Button newQuizButton = findViewById(R.id.newQuizButton);
        Button finishButton = findViewById(R.id.finishButton);

        String userName = getIntent().getStringExtra("USER_NAME");
        int score = getIntent().getIntExtra("SCORE", 0);
        int totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);

        congratsTextView.setText("Congratulations " + userName + "!");
        scoreTextView.setText(score + "/" + totalQuestions);

        newQuizButton.setOnClickListener(v -> {
            // Go directly to QuizActivity with the same username
            Intent intent = new Intent(ResultActivity.this, MathQuizActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
        });

        finishButton.setOnClickListener(v -> {
            finishAffinity();
        });
    }
}