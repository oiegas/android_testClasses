package com.example.lenovo.domain;

import java.util.Set;

/**
 * Created by Lenovo on 4/19/2016.
 */
public class Role {
    private int role_id;
    private String name;
    private Set<User> employee;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getEmployee() {
        return employee;
    }

    public void setEmployee(Set<User> employee) {
        this.employee = employee;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
