package com.example.android_whostolesantasbeard.entities;

public class LeaderboardEntry {
    String username;
    int maxPoints;

    public LeaderboardEntry(String username, int maxScore) {
        this.username = username;
        this.maxPoints = maxScore;
    }

    public String getUsername() {
        return username;
    }

    public int getMaxScore() {
        return maxPoints;
    }
}
