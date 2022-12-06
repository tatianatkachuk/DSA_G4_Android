package com.example.android_whostolesantasbeard;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.widget.Toast;
import android.content.CursorLoader;
import android.content.Loader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity{

    TextView swapToStore;
    IWSSBService service;

    // Loader
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Loader
        progressBar = findViewById(R.id.loaderMain);


        service = APIClient.getClient().create(IWSSBService.class);

        swapToStore = (TextView) findViewById(R.id.swapToStore);

        // Loader finish
        progressBar.setVisibility(View.GONE);


        swapToStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStore();
            }
        });

    }

    public void openStoreView(View view) {
        Intent intent = new Intent(Main.this, ShopItems.class);
        this.startActivity(intent);
    }

    public void openStore() {
        Intent intent = new Intent(this, ShopItems.class);
        startActivity(intent);
    }


}
