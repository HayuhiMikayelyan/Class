package com.example.aclass.auth;

import java.util.List;
import java.util.Map;

public class User {
    private String email;
    private Boolean isTeacher;
    private String name;
    private List<String> classes;
    private String id;
    private String icon;
    private List<Map<String, Integer>> tests;

    public User() {
    }

    public User(String email, Boolean isTeacher, String name, List<String> classes, String id, String icon, List<Map<String, Integer>> tests) {
        this.email = email;
        this.isTeacher = isTeacher;
        this.name = name;
        this.classes = classes;
        this.id = id;
        this.icon = icon;
        this.tests = tests;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(Boolean teacher) {
        isTeacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Map<String, Integer>> getTests() {
        return tests;
    }

    public void setTests(List<Map<String, Integer>> tests) {
        this.tests = tests;
    }
}
