package com.example.lenovo.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AlexandruOi on 5/11/2016.
 */
public class StudentAnswerResponse {
    @SerializedName("testId")
    private int testId;
    @SerializedName("userId")
    private int userId;
    @SerializedName("questionId")
    private int questionId;
    @SerializedName("answerId")
    private int answerId;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
}
