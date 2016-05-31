package com.example.lenovo.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lenovo on 4/19/2016.
 */
public class StudentAnswer {

    private int studentAnswerId;
    private User user;
    private Test test;
    private Question question;
    private Answer answer;

    public int getStudentAnswerId() {
        return studentAnswerId;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public void setStudentAnswerId(int studentAnswerId) {
        this.studentAnswerId = studentAnswerId;

    }
}
