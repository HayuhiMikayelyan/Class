package com.example.aclass.home.classes.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Lesson implements Parcelable {
    private String title;
    private String description;
    private ArrayList<String> tests;

    public Lesson(String title, String description, ArrayList<String> tests) {
        this.title = title;
        this.description = description;
        this.tests = tests;
    }

    public Lesson() {}

    protected Lesson(Parcel in) {
        title = in.readString();
        description = in.readString();
        tests = in.createStringArrayList();
    }

    public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getTests() {
        return tests;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeStringList(tests);
    }
}
