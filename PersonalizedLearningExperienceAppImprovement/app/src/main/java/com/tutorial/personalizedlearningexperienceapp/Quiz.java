package com.tutorial.personalizedlearningexperienceapp;

public class Quiz {
    private String title;
    private String description;
    private String topic;

    public Quiz(String title, String description, String topic) {
        this.title = title;
        this.description = description;
        this.topic = topic;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getTopic() { return topic; }
}
