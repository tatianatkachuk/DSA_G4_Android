package com.example.android_whostolesantasbeard;

public class Issue {
    private String date;
    private String informer;
    private String message;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInformer() {
        return informer;
    }

    public void setInformer(String informer) { this.informer = informer; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) { this.message = message; }


    //Issue Constructor
    public Issue(String date, String informer, String message){
        this.date = date;
        this.informer = informer;
        this.message = message;
    }


    public Issue(){}

    @Override
    public String toString() {
        return "User{" +
                "date='" + date + '\'' +
                ", informer='" + informer + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
