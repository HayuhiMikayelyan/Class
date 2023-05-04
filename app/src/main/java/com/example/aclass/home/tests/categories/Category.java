package com.example.aclass.home.tests.categories;

public class Category {
    private String icon;
    private String name;
    private int count;
    private int id;

    public Category(String icon, String name, int count, int id) {
        this.icon = icon;
        this.name = name;
        this.count = count;
        this.id = id;
    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
