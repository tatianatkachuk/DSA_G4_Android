package com.example.android_whostolesantasbeard;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity{

    TextView swapToStore;
    IWSSBService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = APIClient.getClient().create(IWSSBService.class);

        swapToStore = (TextView) findViewById(R.id.swapToStore);

        swapToStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStoreView(view);
            }
        });

    }

    public void openStoreView(View view) {
        Intent intent = new Intent(Main.this, Object.class);
        this.startActivity(intent);
    }
}
