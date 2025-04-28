package com.tutorial.personalizedlearningexperienceapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private TextView quizTitleTextView;
    private LinearLayout quizContainer, loadingContainer;
    private Button submitButton;
    private List<Question> questions = new ArrayList<>();
    private String topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        topic = getIntent().getStringExtra("topic");
        loadingContainer = findViewById(R.id.loadingContainer);
        quizTitleTextView = findViewById(R.id.quizTitleTextView);
        quizContainer = findViewById(R.id.quizContainer);
        submitButton = findViewById(R.id.submitButton);

        quizTitleTextView.setText(topic + " Quiz");
        fetchQuizQuestions();
    }

    private void fetchQuizQuestions() {
        loadingContainer.setVisibility(View.VISIBLE); // Show loading
        submitButton.setVisibility(View.INVISIBLE);

        String url = "http://10.0.2.2:5000/getQuiz?topic=" + URLEncoder.encode(topic);
        Log.d(TAG, "Request URL: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    loadingContainer.setVisibility(View.GONE); // Hide loading
                    submitButton.setVisibility(View.VISIBLE);
                    try {
                        Log.d(TAG, "Raw API Response: " + response.toString());

                        if (!response.has("quiz")) {
                            throw new JSONException("Response missing 'quiz' field");
                        }

                        JSONArray quizArray = response.getJSONArray("quiz");
                        if (quizArray.length() == 0) {
                            throw new JSONException("Quiz array is empty");
                        }

                        questions.clear();

                        for (int i = 0; i < quizArray.length(); i++) {
                            JSONObject quizQuestion = quizArray.getJSONObject(i);

                            // Validate all required fields exist
                            if (!quizQuestion.has("question") ||
                                    !quizQuestion.has("options") ||
                                    !quizQuestion.has("correct_answer")) {
                                throw new JSONException("Question missing required fields");
                            }

                            String questionText = quizQuestion.getString("question");
                            String correctAnswer = quizQuestion.getString("correct_answer");
                            JSONArray optionsArray = quizQuestion.getJSONArray("options");

                            // Validate options array
                            if (optionsArray.length() < 2) {
                                throw new JSONException("Question must have at least 2 options");
                            }

                            List<String> options = new ArrayList<>();
                            for (int j = 0; j < optionsArray.length(); j++) {
                                options.add(optionsArray.getString(j));
                            }

                            questions.add(new Question(questionText, options, correctAnswer));
                        }

                        displayQuestions();
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Parsing Error", e);
                        Toast.makeText(this, "Error: Invalid quiz format", Toast.LENGTH_LONG).show();
                        setupSampleQuestions();
                    } catch (Exception e) {
                        Log.e(TAG, "Unexpected Error", e);
                        Toast.makeText(this, "Error loading quiz", Toast.LENGTH_LONG).show();
                        setupSampleQuestions();
                    }
                },
                error -> {
                    loadingContainer.setVisibility(View.GONE); // Hide loading
                    submitButton.setVisibility(View.VISIBLE);
                    Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                    setupSampleQuestions();
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000, // 15 seconds timeout
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void setupSampleQuestions() {
        questions.clear();
        questions.add(new Question(
                "Sample question about " + topic,
                Arrays.asList("Option A", "Option B", "Option C", "Option D"),
                "B"
        ));
        displayQuestions();
    }

    private void displayQuestions() {
        quizContainer.removeAllViews();

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            View questionView = getLayoutInflater().inflate(R.layout.item_question, quizContainer, false);
            TextView questionText = questionView.findViewById(R.id.questionText);
            RadioGroup optionsGroup = questionView.findViewById(R.id.optionsGroup);

            questionText.setText((i + 1) + ". " + question.getQuestionText());

            for (int j = 0; j < question.getOptions().size(); j++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(question.getOptions().get(j));
                radioButton.setId(View.generateViewId());
                optionsGroup.addView(radioButton);
            }

            quizContainer.addView(questionView);
        }

        submitButton.setOnClickListener(v -> {
            int score = calculateScore();
            showResults(score);
        });
    }

    private int calculateScore() {
        int score = 0;
        for (int i = 0; i < quizContainer.getChildCount(); i++) {
            View questionView = quizContainer.getChildAt(i);
            RadioGroup optionsGroup = questionView.findViewById(R.id.optionsGroup);
            int selectedId = optionsGroup.getCheckedRadioButtonId();

            if (selectedId != -1) {
                RadioButton selectedRadioButton = questionView.findViewById(selectedId);
                int selectedIndex = optionsGroup.indexOfChild(selectedRadioButton);
                String selectedAnswer = String.valueOf((char) ('A' + selectedIndex));

                if (selectedAnswer.equalsIgnoreCase(questions.get(i).getCorrectAnswer())) {
                    score++;
                }
            }
        }
        return score;
    }

    private void showResults(int score) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("total", questions.size());
        intent.putExtra("topic", topic);
        startActivity(intent);
    }
}