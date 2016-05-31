package com.example.lenovo.domain.network;

import com.example.lenovo.domain.StudentAnswerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AlexandruOi on 5/19/2016.
 */
public class StudentAnswerToken {
    @SerializedName("token")
    private String token;
    @SerializedName("userId")
    private int userId;
    @SerializedName("studentResponse")
    private List<StudentAnswerResponse> studentResponse;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<StudentAnswerResponse> getStudentResponse() {
        return studentResponse;
    }

    public void setStudentResponse(List<StudentAnswerResponse> studentResponse) {
        this.studentResponse = studentResponse;
    }
}
