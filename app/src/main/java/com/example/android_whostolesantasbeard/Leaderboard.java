package com.example.android_whostolesantasbeard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android_whostolesantasbeard.entities.LeaderboardEntry;

import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity {

    ArrayList<LeaderboardEntry> leaderBoardEntries = new ArrayList<>();
    Button goBackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        setUpLeaderBoardEntries();

        LeaderboardRecyclerViewAdapter adapter = new LeaderboardRecyclerViewAdapter(leaderBoardEntries,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        goBackButton = (Button) findViewById(R.id.button_goBack);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    // Must be replaced by api petitions later on
    private void setUpLeaderBoardEntries()
    {
        LeaderboardEntry a = new LeaderboardEntry("Fred",111);
        LeaderboardEntry b = new LeaderboardEntry("Adam",666);
        LeaderboardEntry c = new LeaderboardEntry("Omar",333);
        LeaderboardEntry d = new LeaderboardEntry("Tatiana",444);
        LeaderboardEntry e = new LeaderboardEntry("Bernat",555);
        leaderBoardEntries.add(a);
        leaderBoardEntries.add(b);
        leaderBoardEntries.add(c);
        leaderBoardEntries.add(d);
        leaderBoardEntries.add(e);
    }
}