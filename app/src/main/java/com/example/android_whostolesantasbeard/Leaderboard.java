package com.example.android_whostolesantasbeard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android_whostolesantasbeard.entities.LeaderboardEntry;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Leaderboard extends AppCompatActivity {

    ArrayList<LeaderboardEntry> leaderBoardEntries = new ArrayList<>();
    Button goBackButton;
    IWSSBService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        setUpLeaderBoardEntries();
    }

    private void fillLeaderBoard() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
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
        service = APIClient.getClient().create(IWSSBService.class);
        Call<ArrayList<LeaderboardEntry>> call = service.getLeaderBoard();
        call.enqueue(new Callback<ArrayList<LeaderboardEntry>>() {
            @Override
            public void onResponse(Call<ArrayList<LeaderboardEntry>> call, Response<ArrayList<LeaderboardEntry>> response) {
                if(response.code() == 200){
                    leaderBoardEntries = response.body();
                    fillLeaderBoard();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LeaderboardEntry>> call, Throwable t) {

            }
        });

    }
}