package com.example.aclass.home.classes;

public class Class {
    private String className;
    private String subject;
    private String id;

    public Class(String className, String subject, String id) {
        this.className = className;
        this.subject = subject;
        this.id = id;
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

    public void setClassName(String className) {
        this.className = className;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
