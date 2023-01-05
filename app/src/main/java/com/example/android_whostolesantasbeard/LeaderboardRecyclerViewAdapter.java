package com.example.android_whostolesantasbeard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_whostolesantasbeard.entities.LeaderboardEntry;

import java.util.ArrayList;

public class LeaderboardRecyclerViewAdapter extends RecyclerView.Adapter<LeaderboardRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<LeaderboardEntry> leaderboardEntries;

    public LeaderboardRecyclerViewAdapter(ArrayList<LeaderboardEntry> leaderboardEntries, Context context){
        this.leaderboardEntries = leaderboardEntries;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaderboardRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout (Gives the look to each of our rows)
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_leaderboard_row,parent,false);
        return new LeaderboardRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.username.setText(leaderboardEntries.get(position).getUsername());
        int maxScore = leaderboardEntries.get(position).getMaxScore();
        holder.maxScore.setText(Integer.toString(maxScore));
        Log.d("DEBUG", "We created a row with username : " + holder.username.getText() + " And Score : " + holder.maxScore.getText());
    }

    @Override
    public int getItemCount() {
        // How many items do you want to be displayed
        return leaderboardEntries.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView maxScore;

        public MyViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.leaderboardRow_username);
            maxScore = (TextView) itemView.findViewById(R.id.leaderboardRow_maxscore);
        }
    }
}
