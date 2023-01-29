package com.example.android_whostolesantasbeard;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_whostolesantasbeard.entities.InventoryElement;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class Inventory extends AppCompatActivity {
    ArrayList<InventoryElement> inventoryL = new ArrayList<>();
    Button goBackButton;
    IWSSBService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        service = APIClient.getClient().create(IWSSBService.class);

        Call<ArrayList<InventoryElement>> call = service.getInventory(Main.S_MyID);
        call.enqueue(new Callback<ArrayList<InventoryElement>>() {
            @Override
            public void onResponse(Call<ArrayList<InventoryElement>> call, Response<ArrayList<InventoryElement>> response) {
                inventoryL = response.body();
                fillInventory();
            }
            @Override
            public void onFailure(Call<ArrayList<InventoryElement>> call, Throwable t) {
            }
        });

    }
    private void fillInventory() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.m_recyclerView_inventory);
        InventoryRecyclerViewAdapter adapter = new InventoryRecyclerViewAdapter(inventoryL,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        goBackButton = (Button) findViewById(R.id.button_inven_back);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}