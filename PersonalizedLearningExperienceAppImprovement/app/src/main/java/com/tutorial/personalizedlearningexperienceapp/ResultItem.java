package com.tutorial.personalizedlearningexperienceapp;

public class ResultItem {
    private String label;
    private String value;

    public ResultItem(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}
