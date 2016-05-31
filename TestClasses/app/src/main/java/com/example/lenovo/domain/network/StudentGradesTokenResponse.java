package com.example.lenovo.domain.network;

import com.example.lenovo.domain.StudentGrade;
import com.example.lenovo.domain.TokenResponse;

import java.util.List;

/**
 * Created by AlexandruOi on 5/23/2016.
 */
public class StudentGradesTokenResponse {
    private List<StudentGrade> grades;
    private TokenResponse response;

    public List<StudentGrade> getGrades() {
        return grades;
    }

    public void setGrades(List<StudentGrade> grades) {
        this.grades = grades;
    }

    public TokenResponse getResponse() {
        return response;
    }

    public void setResponse(TokenResponse response) {
        this.response = response;
    }

}
