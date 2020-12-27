package com.example.devmark.model;

public class User {

    private int uuid;
    private String username;
    private String email;

    private User(){

    }

    public User(int uuid, String username, String email){
        this.uuid = uuid;
        this.username = username;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
