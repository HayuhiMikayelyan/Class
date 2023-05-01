package com.example.aclass.home.classes;

public class Class {
    private String className;
    private String subject;
    private String id;
    private int members;

    public Class(String className, String subject, String id, int members) {
        this.className = className;
        this.subject = subject;
        this.id = id;
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

    public int getMembers() {
        return members;
    }
}
