package com.example.android_whostolesantasbeard;

public class User {
    private String username;
    private String password;
    private String email;
    private String id;
    private String coins;
    private Integer image;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return email;
    }

    public void setMail(String email) {
        this.email = email;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public Integer getImageID() {
        return image;
    }

    public void setImageID(Integer image) {
        this.image = image;
    }


    //User Constructor
    public User(String id, String username, String password, String email, String coins, Integer image){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.coins = coins;
        this.image = image;
    }


    public User(){}

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
