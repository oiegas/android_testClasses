package com.example.lenovo.domain.network;

import com.example.lenovo.domain.Test;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AlexandruOi on 5/20/2016.
 */
public class TestToken {
    @SerializedName("token")
    private String token;
    @SerializedName("test")
    private Test test;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
