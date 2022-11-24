package com.example.android_whostolesantasbeard.entities;

public class UserCredentials {
    String username;
    String password;
    String email;
    public UserCredentials(String name,String email,String password){
        this.username = name;
        this.email = email;
        this.password = password;
    }
}
