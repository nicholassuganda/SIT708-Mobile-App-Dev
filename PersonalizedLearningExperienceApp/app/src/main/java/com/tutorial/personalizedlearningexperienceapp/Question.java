package com.tutorial.personalizedlearningexperienceapp;

import java.util.List;

public class Question {
    private final String questionText;
    private final List<String> options;
    private final String correctAnswer;

    public Question(String questionText, List<String> options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer.toUpperCase(); // Ensure uppercase
    }

    // Getters and setters
    public String getQuestionText() { return questionText; }

    public List<String> getOptions() { return options; }

    public String getCorrectAnswer() { return correctAnswer; }
}
