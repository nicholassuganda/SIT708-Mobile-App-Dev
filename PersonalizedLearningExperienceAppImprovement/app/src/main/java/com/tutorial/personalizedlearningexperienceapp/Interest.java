package com.tutorial.personalizedlearningexperienceapp;

public class Interest {
    private String name;
    private boolean selected;

    public Interest(String name, boolean selected) {
        this.name = name;
        this.selected = selected;
    }

    // Getters and setters
    public String getName() { return name; }
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
}
