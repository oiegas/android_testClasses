package com.example.lenovo.domain;

import com.example.lenovo.domain.Answer;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * Created by Lenovo on 4/19/2016.
 */
public class Question {

    private int questionId;
    private String question;
    private Test test;
    private Set<Answer> answers;
    private Set<StudentAnswer> studAnswer;

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

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Set<StudentAnswer> getStudAnswer() {
        return studAnswer;
    }

    public void setStudAnswer(Set<StudentAnswer> studAnswer) {
        this.studAnswer = studAnswer;
    }
}
