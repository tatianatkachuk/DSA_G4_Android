package com.example.android_whostolesantasbeard;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity{

    TextView swapToStore;
    TextView personalInfo;
    Button settingsSwap;
    TextView leaderBoard;
    Button closeSession;
    IWSSBService service;

    TextView nameText;
    TextView coinsText;

    String username;

    User user = new User("", "", "", "", "");
    // Loader
    private ProgressBar progressBar;

    // Shared preferences
    SharedPreferences shPrefs;
    public static final String PREFERENCES = "prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Loader
        progressBar = findViewById(R.id.loaderMain);

        service = APIClient.getClient().create(IWSSBService.class);

        String username = getIntent().getExtras().getString("username");
        getUserInfo(service, username, "main");


        swapToStore = (TextView) findViewById(R.id.swapToStore);
        personalInfo = (TextView) findViewById(R.id.personalInfo);
        settingsSwap = (Button) findViewById(R.id.settingsSwap);
        closeSession = (Button) findViewById(R.id.closeSession);
        nameText = (TextView) findViewById(R.id.nameText);
        coinsText = (TextView) findViewById(R.id.textCoins);
        leaderBoard = (TextView) findViewById(R.id.leaderboard);


        // Loader finish
        progressBar.setVisibility(View.GONE);


        swapToStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStore();
            }
        });

        personalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getIntent().getExtras().getString("username");
                getUserInfo(service, username, "info");
            }
        });

        settingsSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getIntent().getExtras().getString("username");
                getUserInfo(service, username, "settings");
            }
        });

        closeSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut(view);
            }
        });
        leaderBoard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openLeaderboard();
            }
        });

    }

    ///////////////////////////////////////////
    ///////////////user Info///////////////////
    ///////////////////////////////////////////

    public void getUserInfo(IWSSBService service, String username, String activity){
        Call<User> call = service.getUserbyUserName(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==201){
                    User user = response.body();
                    if(activity == "main"){setUserInMain(user);}
                    if(activity == "info"){openProfileInfo(user);}
                    if(activity == "settings"){openSettings(user);}
                    return;
                }
                if(response.code()==404){
                    Log.d("Error","User not found");
                    Toast toast = Toast.makeText(getApplicationContext(),"This user doesn't exist! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {toast.show();}
                    });
                    return;
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
                Log.d("Error","Failure");
            }
        });

    }

    public void setUserInMain(User user){
        nameText.setText(user.getUsername());
        coinsText.setText(user.getCoins() + " coins");
    }

    ///////////////////////////////////////////
    ///////////////open Views//////////////////
    ///////////////////////////////////////////

    public void logOut(View view) {
        SharedPreferences shPrefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shPrefs.edit();
        editor.putString("username", "");
        editor.putString("password", "");
        editor.commit();
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
        finish();
    }

    public void openStoreView(View view) {
        Intent intent = new Intent(this, Store.class);
        this.startActivity(intent);
    }

    public void openStore() {
        Intent intent = new Intent(this, Store.class);
        this.startActivity(intent);
    }
    public void openLeaderboard(){
        Intent intent = new Intent(this, Leaderboard.class);
        this.startActivity(intent);
    }

    public void openProfileInfo(User user) {
        Intent intent = new Intent(this, InfoProfile.class);
        Log.d("ID::::", user.getId());
        intent.putExtra("id", user.getId());
        intent.putExtra("username", user.getUsername());
        intent.putExtra("mail", user.getMail());
        intent.putExtra("coins", user.getCoins());
        startActivity(intent);
    }


    public void openSettings(User user) {
        Intent intent = new Intent(this, Settings.class);
        intent.putExtra("id", user.getId());
        intent.putExtra("username", user.getUsername());
        intent.putExtra("mail", user.getMail());
        startActivity(intent);
    }
}
