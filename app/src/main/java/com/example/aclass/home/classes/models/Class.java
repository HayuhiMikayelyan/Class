package com.example.aclass.home.classes.models;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Objects;

public class Class implements Parcelable {
    private String className;
    private String subject;
    private String id;
    private int membersCount;
    private ArrayList<String> members;
    private ArrayList<Lesson> lessons;
    @Nullable
    private Object obj;

    public Class(String className, String subject, String id, int membersCount, ArrayList<String> members, ArrayList<Lesson> lessons) {
        this.className = className;
        this.subject = subject;
        this.id = id;
        this.membersCount = membersCount;
        this.members = members;
        this.lessons = lessons;
    }

    protected Class(Parcel in) {
        className = in.readString();
        subject = in.readString();
        id = in.readString();
        membersCount = in.readInt();
        members = in.createStringArrayList();
    }

    public static final Creator<Class> CREATOR = new Creator<Class>() {
        @Override
        public Class createFromParcel(Parcel in) {
            return new Class(in);
        }

        @Override
        public Class[] newArray(int size) {
            return new Class[size];
        }
    };

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

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(className);
        dest.writeString(subject);
        dest.writeString(id);
        dest.writeInt(membersCount);
        dest.writeStringList(members);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Class class1 = (Class) obj;
        return Objects.requireNonNull(class1).id.equals(id);
    }
}
