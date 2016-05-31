package com.example.lenovo.domain;

import java.util.List;

/**
 * Created by AlexandruOi on 5/8/2016.
 */
public class QuestionAndroid {
    private int testId;
    private int questionId;
    private String question;
    private boolean available;
    private List<AnswerAndroid> answers;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<AnswerAndroid> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerAndroid> answers) {
        this.answers = answers;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }
}