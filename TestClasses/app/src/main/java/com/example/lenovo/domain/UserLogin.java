package com.example.lenovo.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AlexandruOi on 5/2/2016.
 */
public class UserLogin {
    @SerializedName("userId")
    private int userId;
    @SerializedName("token")
    private String token;
    @SerializedName("role")
    private Role role;
    @SerializedName("username")
    private String username;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}