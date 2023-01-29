package com.example.android_whostolesantasbeard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_whostolesantasbeard.entities.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Store extends AppCompatActivity {
    ArrayList<Item> itemsList ;
    Button returnButton;
    IWSSBService service;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        returnButton = (Button) findViewById(R.id.store_return);
        service =  APIClient.getClient().create(IWSSBService.class);
        setUpItemsList();
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void SetUpUI() {
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        StoreRecyclerViewAdapter adapter = new StoreRecyclerViewAdapter(this,itemsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // This should be provided by the server
    private void setUpItemsList(){

        Call<ArrayList<Item>> call = service.getStoreItems();
        call.enqueue(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                int responseCode = response.code();
                switch (responseCode){
                    case 200 : {
                        itemsList = response.body();
                        SetUpUI();
                        break;
                    }
                    case 402 : {
                        break;
                    }
                    case 409 : {
                        break;
                    }
                }



            }
            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {

            }
        });
    }
}