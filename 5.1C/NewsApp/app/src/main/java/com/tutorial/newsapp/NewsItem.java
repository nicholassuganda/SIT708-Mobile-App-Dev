package com.tutorial.newsapp;

import java.io.Serializable;

public class NewsItem implements Serializable {
    private String title;
    private String description;
    private int imageUrl;
    private boolean isTopStory;

    public NewsItem(String title, String description, int imageUrl, boolean isTopStory) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isTopStory = isTopStory;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getImageUrl() { return imageUrl; }
    public boolean isTopStory() { return isTopStory; }
}
