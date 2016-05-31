package com.example.lenovo.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * Created by Lenovo on 4/19/2016.
 */
public class Answer {
    @SerializedName("answerId")
    private int answerId;
    @SerializedName("answer")
    private String answer;
    @SerializedName("good")
    private boolean good;
    @SerializedName("question")
    private Question question;
    @SerializedName("studAnswer")
    private Set<StudentAnswer> studAnswer;

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Set<StudentAnswer> getStudAnswer() {
        return studAnswer;
    }

    public void setStudAnswer(Set<StudentAnswer> studAnswer) {
        this.studAnswer = studAnswer;
    }
}
