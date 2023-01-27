package com.example.android_whostolesantasbeard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_whostolesantasbeard.entities.Item;

import java.util.ArrayList;

public class Store extends AppCompatActivity {
    ArrayList<Item> itemsList = new ArrayList<>();
    Button returnButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        returnButton = (Button) findViewById(R.id.store_return);
        // Temporal

        setUpItemsList();

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        StoreRecyclerViewAdapter adapter = new StoreRecyclerViewAdapter(this,itemsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    // This should be provided by the server
    private void setUpItemsList(){
        Item item0 = new Item("Pan","Este pan mágico te permite ver colores.","100","breadImage.jpg");
        Item item1 = new Item("Agua","¿Qué? También tendrás que hidratarte de vez en cuando,¿No?.","2","waterImage.jpg");
        Item item2 = new Item("Galleta","Como las galletas que hace tu abuela.","5","cookieImage.jpg");
        itemsList.add(item0);
        itemsList.add(item1);
        itemsList.add(item2);
    }
}