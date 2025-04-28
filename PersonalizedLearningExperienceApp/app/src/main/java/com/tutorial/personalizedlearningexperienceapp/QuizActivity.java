package com.tutorial.personalizedlearningexperienceapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LearningActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button submitButton, continueButton;
    private ProgressBar loadingProgressBar;
    private LinearLayout quizContainer, resultsContainer;

    private List<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        // Initialize UI components
        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        continueButton = findViewById(R.id.continueButton);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        quizContainer = findViewById(R.id.quizContainer);
        resultsContainer = findViewById(R.id.resultsContainer);

        // Initially hide results container
        resultsContainer.setVisibility(View.GONE);

        // Load quiz questions
        loadQuizQuestions();

        submitButton.setOnClickListener(v -> checkAnswer());
        continueButton.setOnClickListener(v -> showNextQuestion());
    }

    private void loadQuizQuestions() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        quizContainer.setVisibility(View.GONE);

        // URL for getting quiz questions based on user interests
        String url = "http://10.0.2.2:5000/getPersonalizedQuiz";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    loadingProgressBar.setVisibility(View.GONE);
                    quizContainer.setVisibility(View.VISIBLE);

                    try {
                        JSONArray quizArray = response.getJSONArray("quiz");
                        for (int i = 0; i < quizArray.length(); i++) {
                            JSONObject questionObj = quizArray.getJSONObject(i);
                            Question question = new Question(
                                    questionObj.getString("question"),
                                    questionObj.getJSONArray("options"),
                                    questionObj.getString("correct_answer")
                            );
                            questions.add(question);
                        }

                        if (!questions.isEmpty()) {
                            displayQuestion(questions.get(0));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing quiz questions", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    loadingProgressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Failed to load quiz: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        // Add request to queue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void displayQuestion(Question question) {
        questionTextView.setText((currentQuestionIndex + 1) + ". " + question.getQuestionText());

        optionsRadioGroup.removeAllViews();
        optionsRadioGroup.clearCheck();

        List<String> options = question.getOptions();
        for (int i = 0; i < options.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(options.get(i));
            radioButton.setId(i);
            optionsRadioGroup.addView(radioButton);
        }
    }

    private void checkAnswer() {
        int selectedId = optionsRadioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedId);
        String selectedAnswer = selectedRadioButton.getText().toString();

        Question currentQuestion = questions.get(currentQuestionIndex);
        if (currentQuestion.getCorrectAnswer().equals(selectedAnswer)) {
            score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect. The correct answer is: " + currentQuestion.getCorrectAnswer(), Toast.LENGTH_SHORT).show();
        }

        // Disable further selection
        for (int i = 0; i < optionsRadioGroup.getChildCount(); i++) {
            optionsRadioGroup.getChildAt(i).setEnabled(false);
        }

        submitButton.setEnabled(false);
        continueButton.setVisibility(View.VISIBLE);
    }

    private void showNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            // Show next question
            continueButton.setVisibility(View.GONE);
            submitButton.setEnabled(true);
            displayQuestion(questions.get(currentQuestionIndex));

            // Re-enable options
            for (int i = 0; i < optionsRadioGroup.getChildCount(); i++) {
                optionsRadioGroup.getChildAt(i).setEnabled(true);
            }
        } else {
            // Quiz completed, show results
            showResults();
        }
    }

    private void showResults() {
        quizContainer.setVisibility(View.GONE);
        resultsContainer.setVisibility(View.VISIBLE);

        TextView resultsTextView = findViewById(R.id.resultsTextView);
        resultsTextView.setText("You scored " + score + " out of " + questions.size());

        // You could add more detailed results here showing each question and correct answer
    }
}