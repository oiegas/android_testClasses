package com.example.lenovo.domain.network;

import com.example.lenovo.domain.Test;
import com.example.lenovo.domain.TokenResponse;

import java.util.List;

/**
 * Created by AlexandruOi on 5/23/2016.
 */
public class TestTokenResponse {

    private List<Test> tests;
    private TokenResponse tokenResponse;

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }

    public void setTokenResponse(TokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }

}
