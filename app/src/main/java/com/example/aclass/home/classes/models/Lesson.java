package com.example.aclass.home.classes.models;

import java.util.ArrayList;

public class Lesson {
    private String title;
    private String description;
    private ArrayList<String> tests;

    public Lesson(String title, String description, ArrayList<String> tests) {
        this.title = title;
        this.description = description;
        this.tests = tests;
    }

    public Lesson() {}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getTests() {
        return tests;
    }
}
