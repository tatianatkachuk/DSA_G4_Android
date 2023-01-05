package com.example.android_whostolesantasbeard.entities;

public class LeaderboardEntry {
    String username;
    int maxScore;

    public LeaderboardEntry(String username, int maxScore) {
        this.username = username;
        this.maxScore = maxScore;
    }

    public String getUsername() {
        return username;
    }

    public int getMaxScore() {
        return maxScore;
    }
}
