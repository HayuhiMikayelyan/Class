package com.example.aclass.home.tests.categories;

public class Category {
    private String icon;
    private String name;
    private int count;

    public Category(String icon, String name, int count) {
        this.icon = icon;
        this.name = name;
        this.count = count;
    }

    public Category() {
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
