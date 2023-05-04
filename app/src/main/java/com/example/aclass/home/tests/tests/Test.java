package com.example.aclass.home.tests.tests;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Test implements Parcelable {
    private String id;
    private String name;
    private ArrayList<String> rightAnswer;
    private ArrayList<ArrayList<String>> answers;
    private ArrayList<String> questions;

    public Test() {
    }

    public Test(String id, String name, ArrayList<String> rightAnswer, ArrayList<ArrayList<String>> answers, ArrayList<String> questions) {
        this.id = id;
        this.name = name;
        this.rightAnswer = rightAnswer;
        this.answers = answers;
        this.questions = questions;
    }

    protected Test(Parcel in) {
        id = in.readString();
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
        dest.writeString(id);
        dest.writeString(name);
        dest.writeStringList(rightAnswer);
        dest.writeStringList(questions);
    }

    public String getId() {
        return id;
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

    public void addRightAnswer(String rightAnswer) {
        this.rightAnswer.add(rightAnswer);
    }

    public void addAnswers(ArrayList<String> variants) {
        answers.add(variants);
    }

    public void addQuestion(String question) {
        questions.add(question);
    }
}
