package com.example.lenovo.domain.network;

import com.example.lenovo.domain.QuestionAndroid;
import com.example.lenovo.domain.TokenResponse;

import java.util.List;

/**
 * Created by AlexandruOi on 5/23/2016.
 */
public class ListQuestionsTokenResponse {

    private List<QuestionAndroid> questions;
    private TokenResponse tokenResponse;
    public List<QuestionAndroid> getQuestions() {
        return questions;
    }
    public void setQuestions(List<QuestionAndroid> questions) {
        this.questions = questions;
    }
    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }
    public void setTokenResponse(TokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }
}