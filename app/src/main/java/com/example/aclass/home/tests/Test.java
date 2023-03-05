package com.example.aclass.home.tests;

import java.util.ArrayList;

public class Test {
    private int id;
    private String name;
    private String rightAnswer;
    private ArrayList<String> answers;
    private ArrayList<String> questions;

    public Test() {}

    public int getId() {
        return id;
    }


    public String getRightAnswer() {
        return rightAnswer;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public Test(int id, String name, String rightAnswer, ArrayList<String> answers, ArrayList<String> questions) {
        this.id = id;
        this.name = name;
        this.rightAnswer = rightAnswer;
        this.answers = answers;
        this.questions = questions;
    }

}
