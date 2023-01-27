package com.example.android_whostolesantasbeard;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity{

    ImageView profileImage, askImg;

    TextView swapToStore;
    TextView personalInfo;
    Button settingsSwap;
    TextView leaderBoard;
    Button closeSession;
    TextView issues;
    IWSSBService service;

    TextView nameText;
    TextView coinsText;

    String username;

    String selectedImage;

    User user = new User("", "", "", "", "", 0);
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

        profileImage = (ImageView) findViewById(R.id.profile_image);
        askImg = (ImageView) findViewById(R.id.askImg);

        swapToStore = (TextView) findViewById(R.id.swapToStore);
        personalInfo = (TextView) findViewById(R.id.personalInfo);
        settingsSwap = (Button) findViewById(R.id.settingsSwap);
        closeSession = (Button) findViewById(R.id.closeSession);
        issues = (TextView) findViewById(R.id.issues);
        nameText = (TextView) findViewById(R.id.nameText);
        coinsText = (TextView) findViewById(R.id.textCoins);
        leaderBoard = (TextView) findViewById(R.id.leaderboard);


        // Loader finish
        progressBar.setVisibility(View.GONE);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(username);
            }
        });

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

        issues.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openIssue();
            }
        });

        askImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QueryActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

    }

    ///////////////////////////////////////////
    ///////////////profile Image///////////////
    ///////////////////////////////////////////

    public void selectImage(String usr) {
        Intent intent = new Intent(this, SelectImage.class);
        intent.putExtra("username", usr);
        this.startActivity(intent);
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
                    String prueba = user.getId();
                    Log.d("ID user", String.valueOf(prueba));

                    if(activity == "main"){setUserInMain(user);}
                    if(activity == "info"){openProfileInfo(user);}
                    if(activity == "settings"){openSettings(user);}

                    Integer imageID = user.getImageID();

                    Log.d("IMAGE", String.valueOf(imageID));

                    if(imageID.intValue() == 1){
                        Log.d("aqui 1", String.valueOf(imageID));
                        profileImage.setImageResource(R.drawable.avatar1);
                        return;}
                    if(imageID == 2){
                        Log.d("aqui 2", String.valueOf(imageID));
                        profileImage.setImageResource(R.drawable.avatar2);
                        return;}
                    if(imageID == 3){
                        Log.d("aqui 3", String.valueOf(imageID));
                        profileImage.setImageResource(R.drawable.avatar3);
                        return;}
                    if(imageID == 4){
                        Log.d("aqui 4", String.valueOf(imageID));
                        profileImage.setImageResource(R.drawable.avatar4);
                        return;}

                    Log.d("aqui 0", String.valueOf(imageID));
                    profileImage.setImageResource(R.drawable.noavatar);


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

    public void openIssue() {
        Intent intent = new Intent(this, ReportIssue.class);
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
