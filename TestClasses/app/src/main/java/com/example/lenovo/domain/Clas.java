package com.example.lenovo.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * Created by Lenovo on 4/19/2016.
 */
public class Clas {

    @SerializedName("classId")
    private int classId;
    @SerializedName("name")
    private String name;
    public Set<User> users;
    public Set<Test> tests;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Test> getTests() {
        return tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }



    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return name;
    }
}
