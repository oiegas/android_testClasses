package com.example.lenovo.testclasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lenovo on 4/12/2016.
 */
public class ApiError {

    @SerializedName("ErrorCode")
    private String errorCode;
    @SerializedName("ErrorMessage")
    private String errorMessage;

    private ApiError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
