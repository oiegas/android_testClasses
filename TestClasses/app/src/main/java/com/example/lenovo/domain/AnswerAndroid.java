package com.example.lenovo.domain;

/**
 * Created by AlexandruOi on 5/8/2016.
 */
public class AnswerAndroid {
    private int answerId;
    private String answer;
    private boolean good;

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

}