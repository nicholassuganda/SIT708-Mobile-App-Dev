package com.tutorial.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MathQuizActivity extends AppCompatActivity {
    TextView welcomeTextView, questionTextView, progressTextView;
    RadioGroup optionsRadioGroup;
    RadioButton option1RadioButton, option2RadioButton, option3RadioButton;
    Button submitButton;
    ProgressBar progressBar;

    String userName;

    List<Question> questionsList;
    int currentQuestionIndex = 0;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_math_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views components
        welcomeTextView = findViewById(R.id.welcomeTextView);
        questionTextView = findViewById(R.id.questionTextView);
        progressTextView = findViewById(R.id.progressTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        option1RadioButton = findViewById(R.id.option1RadioButton);
        option2RadioButton = findViewById(R.id.option2RadioButton);
        option3RadioButton = findViewById(R.id.option3RadioButton);
        submitButton = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.progressBar);

        // Get user name
        userName = getIntent().getStringExtra("USER_NAME");

        // Set welcome message
        welcomeTextView.setText("Welcome " + userName + "!");

        // Load questions from database
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        questionsList = databaseHelper.getAllQuestions();



        // Set progress bar max value
        progressBar.setMax(questionsList.size());

        // Show the first question
        showQuestion(currentQuestionIndex);

        submitButton.setOnClickListener(v -> {
            int selectedOptionId = optionsRadioGroup.getCheckedRadioButtonId();
            if (selectedOptionId == -1) {
                Toast.makeText(MathQuizActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadioButton = findViewById(selectedOptionId);
            int selectedOption = optionsRadioGroup.indexOfChild(selectedRadioButton) + 1;
            int correctAnswer = questionsList.get(currentQuestionIndex).getAnswer();

            // Disable all radio buttons after selection
            option1RadioButton.setEnabled(false);
            option2RadioButton.setEnabled(false);
            option3RadioButton.setEnabled(false);




            if (selectedOption == correctAnswer) {
                // Highlight correct answer in green
                selectedRadioButton.setBackgroundColor(Color.GREEN);
                score++;
            } else {
                // Highlight wrong answer in red
                selectedRadioButton.setBackgroundColor(Color.RED);
            }

            new Handler().postDelayed(() -> {
                currentQuestionIndex++;
                if (currentQuestionIndex < questionsList.size()) {
                    showQuestion(currentQuestionIndex);
                    // Re-enable radio buttons for next question
                    option1RadioButton.setEnabled(true);
                    option2RadioButton.setEnabled(true);
                    option3RadioButton.setEnabled(true);
                } else {
                    showResult();
                }
            }, 2000); // 2 second delay to see the feedback
        });
    }

    public void showQuestion(int index) {
        Question currentQuestion = questionsList.get(index);

        questionTextView.setText(currentQuestion.getQuestion());
        option1RadioButton.setText(currentQuestion.getOption1());
        option2RadioButton.setText(currentQuestion.getOption2());
        option3RadioButton.setText(currentQuestion.getOption3());

        // Reset all radio button to default
        resetRadioButtonAppearance();
        optionsRadioGroup.clearCheck();

        // Update progress
        progressBar.setProgress(index + 1);
        progressTextView.setText((index + 1) + "/" + questionsList.size());

        // Change button text for last question
        if (index == questionsList.size() - 1) {
            submitButton.setText("Submit");
        }
    }

    private void resetRadioButtonAppearance() {
        option1RadioButton.setBackgroundColor(Color.TRANSPARENT);
        option2RadioButton.setBackgroundColor(Color.TRANSPARENT);
        option3RadioButton.setBackgroundColor(Color.TRANSPARENT);
    }

    public void showResult() {
        Intent intent = new Intent(MathQuizActivity.this, ResultActivity.class);
        intent.putExtra("USER_NAME", userName);
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL_QUESTIONS", questionsList.size());
        startActivity(intent);
        finish();
    }


}