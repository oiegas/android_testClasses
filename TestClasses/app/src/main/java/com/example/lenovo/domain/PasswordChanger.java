package com.example.lenovo.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AlexandruOi on 5/5/2016.
 */
public class PasswordChanger {
    @SerializedName("email")
    private String email;
    @SerializedName("code")
    private String code;
    @SerializedName("password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
