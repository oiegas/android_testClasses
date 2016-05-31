package com.example.lenovo.domain.network;

import com.example.lenovo.domain.Test;
import com.example.lenovo.domain.TokenResponse;

/**
 * Created by AlexandruOi on 5/23/2016.
 */
public class SingleTestTokenResponse {
    private Test test;
    private TokenResponse tokenResponse;

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }

    public void setTokenResponse(TokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }
}
