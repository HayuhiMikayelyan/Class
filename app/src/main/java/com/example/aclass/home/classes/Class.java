package com.example.aclass.home.classes;


import java.util.ArrayList;

public class Class {
    private String className;
    private String subject;
    private String id;
    private int membersCount;
    private ArrayList<String> members;

    public Class(String className, String subject, String id, int membersCount, ArrayList<String> members) {
        this.className = className;
        this.subject = subject;
        this.id = id;
        this.membersCount = membersCount;
        this.members = members;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class() {
    }

    public String getClassName() {
        return className;
    }


    public String getSubject() {
        return subject;
    }

    public int getMembersCount() {
        return membersCount;
    }


    public ArrayList<String> getMembers() {
        return members;
    }
}
