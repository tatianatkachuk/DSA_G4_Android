package com.example.android_whostolesantasbeard.entities;

public class Question {

    String id;
    String question;
    String answer;

    //constructor vacío

    public Question(){}
    public Question(String id, String question, String answer){}

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
