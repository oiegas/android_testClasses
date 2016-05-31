package com.example.lenovo.domain.network;

import com.example.lenovo.domain.Question;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AlexandruOi on 5/23/2016.
 */
public class QuestionToken {

    @SerializedName("question")
    private Question question;
    @SerializedName("token")
    private String token;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

