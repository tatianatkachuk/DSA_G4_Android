package com.example.android_whostolesantasbeard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

public class splashScreen extends AppCompatActivity {

    Handler handler = new Handler();
    SharedPreferences shPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        shPrefs = getSharedPreferences(SignIn.PREFERENCES, Context.MODE_PRIVATE);
        boolean isLogged = shPrefs.getBoolean("isLogged",false);
        Intent intent;

        if(!isLogged){
         intent = new Intent(splashScreen.this, SignIn.class);
        }
        else{
         intent = new Intent(splashScreen.this, Main.class);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 3000);



    }
}