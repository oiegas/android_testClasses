package com.example.lenovo.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lenovo on 4/19/2016.
 */
public class Grade {

    private int gradeId;
    private int grade;
    private User user;
    private Test test;

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }


}
