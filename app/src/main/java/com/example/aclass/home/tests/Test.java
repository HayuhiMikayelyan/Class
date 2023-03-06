package com.example.aclass.home.tests;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Test implements Parcelable {
    private int id;
    private int progress;
    private String name;
    private ArrayList<String> rightAnswer;
    private ArrayList<ArrayList<String>> answers;
    private ArrayList<String> questions;

    public Test() {}

    public Test(int id, int progress, String name, ArrayList<String> rightAnswer, ArrayList<ArrayList<String>> answers, ArrayList<String> questions) {
        this.id = id;
        this.progress = progress;
        this.name = name;
        this.rightAnswer = rightAnswer;
        this.answers = answers;
        this.questions = questions;
    }

    protected Test(Parcel in) {
        id = in.readInt();
        progress = in.readInt();
        name = in.readString();
        rightAnswer = in.createStringArrayList();
        questions = in.createStringArrayList();
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(progress);
        dest.writeString(name);
        dest.writeStringList(rightAnswer);
        dest.writeStringList(questions);
    }

    public int getId() {
        return id;
    }

    public int getProgress() {
        return progress;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getRightAnswer() {
        return rightAnswer;
    }

    public ArrayList<ArrayList<String>> getAnswers() {
        return answers;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }
}
