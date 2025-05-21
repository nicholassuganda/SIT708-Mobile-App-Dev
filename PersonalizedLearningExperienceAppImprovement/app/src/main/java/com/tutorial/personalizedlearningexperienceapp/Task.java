package com.tutorial.personalizedlearningexperienceapp;

public class Task {
    private String title;
    private String description;
    private boolean generatedByAI;
    private String topic;

    public Task(String title, String description, boolean generatedByAI, String topic) {
        this.title = title;
        this.description = description;
        this.generatedByAI = generatedByAI;
        this.topic = topic;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isGeneratedByAI() { return generatedByAI; }
    public String getTopic() { return topic; }
}
