package com.tutorial.personalizedlearningexperienceapp;

import java.util.Locale;

public class QuizResult {
    private int id;
    private String topic;
    private int score;
    private int total;
    private String date;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getPercentage() {
        return String.format(Locale.getDefault(), "%.1f%%", (score * 100.0) / total);
    }
}
