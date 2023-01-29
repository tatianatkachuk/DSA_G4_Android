package com.example.android_whostolesantasbeard;

import android.os.Bundle;
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
        setUpInventory(getIntent().getExtras().getString("username"));
    }

    private void fillInventory() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.iRView);
        InventoryRecyclerViewAdapter adapter = new InventoryRecyclerViewAdapter(inventoryL,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        goBackButton = (Button) findViewById(R.id.back);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setUpInventory(String username) {
        service = APIClient.getClient().create(IWSSBService.class);

        Call<User> call = service.getUserbyUserName(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG", response.code() + "");
                if(response.code()==201){
                    User user = response.body();
                    String id = user.getId();
                    Log.d("ID user", String.valueOf(id));

                    Call<ArrayList<InventoryElement>> call2 = service.getInventory(id);
                    call2.enqueue(new Callback<ArrayList<InventoryElement>>() {
                        @Override
                        public void onResponse(Call<ArrayList<InventoryElement>> call2, Response<ArrayList<InventoryElement>> response) {
                            if(response.code() == 200){
                                inventoryL = response.body();
                                fillInventory();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<InventoryElement>> call2, Throwable t) {

                        }
                    });
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
}
