package com.soa.lab4.data;

public class User {
    private String name;
    private String password;
    private int points;

    public User() {
    }

    public User(String name, String password, int points) {
        this.name = name;
        this.password = password;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
