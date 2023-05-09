package com.example.aclass.home.classes.models;

public class Lesson {
    private String title;
    private String description;

    public Lesson(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Lesson() {}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
