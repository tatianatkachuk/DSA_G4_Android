package com.example.android_whostolesantasbeard.entities;

public class SignInCredentials {
    String username;
    String password;
    public SignInCredentials(String name, String email, String password){
        this.username = name;
        this.password = password;
    }
}
