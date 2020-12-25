package com.example.devmark.model;

public class User {

    private int uuid;
    private String username;

    private User(){

    }

    public User(int uuid, String username){
        this.uuid = uuid;
        this.username = username;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
