package com.example.lenovo.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * Created by Lenovo on 4/19/2016.
 */
public class User implements Parcelable {

    @SerializedName("userId")
    private int userId;
    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("email")
    private String email;
    private Role role;
    private Set<Test> tests;
    @SerializedName("classForUser")
    private Clas classForUser;
    @SerializedName("testsMadeByUser")
    public Set<Test> testsMadeByUser;
    private Set<Grade> grades;
    private Set<StudentAnswer> studAnswer;

    public User() {

    }

    public static User create() {
        return new User();
    }

    protected User(Parcel in) {
        userId = in.readInt();
        name = in.readString();
        username = in.readString();
        password = in.readString();
        email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User email(String email) {
        this.email = email;
        return this;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    public User username(String username) {
        this.username = username;
        return this;
    }

    public User name(String name) {
        this.name = name;
        return this;
    }

    public User userId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Test> getTests() {
        return tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }

    public Clas getClassForUser() {
        return classForUser;
    }

    public void setClassForUser(Clas classForUser) {
        this.classForUser = classForUser;
    }

    public Set<Test> getTestsMadeByUser() {
        return testsMadeByUser;
    }

    public void setTestsMadeByUser(Set<Test> testsMadeByUser) {
        this.testsMadeByUser = testsMadeByUser;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    public Set<StudentAnswer> getStudAnswer() {
        return studAnswer;
    }

    public void setStudAnswer(Set<StudentAnswer> studAnswer) {
        this.studAnswer = studAnswer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
    }
}
