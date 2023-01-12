package com.example.android_whostolesantasbeard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_whostolesantasbeard.entities.Item;
import com.example.android_whostolesantasbeard.entities.LeaderboardEntry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Store extends AppCompatActivity {
    ArrayList<Item> items = new ArrayList<>();
    Button goBackButton;
    ItemsStoreRecyclerViewAdapter adapter = new ItemsStoreRecyclerViewAdapter(items);
    private final String TAG = Store.class.getSimpleName();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.m2_recyclerView);

        doApiCall();

        ItemsStoreRecyclerViewAdapter adapter = new ItemsStoreRecyclerViewAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        goBackButton = (Button) findViewById(R.id.button2_goBack);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    //
    private void doApiCall()
    {
        IWSSBService service = APIClient.getClient().create(IWSSBService.class);
        Call<List<Item>> call = service.itemsList();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                //set the results to the adapter
                if (response.code() == 200) {
                    adapter.setData(response.body());
                    Log.d("Success", "Store fetched correctly!");
                    Toast toast = Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });
                }
                ;
                if (response.code() == 500) {
                    Log.d("Error", "Something went wrong with the server");
                    Toast toast = Toast.makeText(getApplicationContext(), "Server Error! Please try again.", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                String msg = "Error in retrofit:" + t.toString();
                Log.d(TAG,msg);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);

            }
        });


    }
      /* IWSSBService service = APIClient.getClient().create(IWSSBService.class);
       Call<List<Item>> call = service.itemsList(items);
       call.enqueue(new Callback<List<Item>>() {
           @Override
           public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
               //set the results to the adapter
               if(response.code() == 200){
                   adapter.setData(response.body());
                   Log.d("Success", "Store fetched correctly!");
                   Toast toast = Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG);
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           toast.show();
                       }
                   });
                 };
               if(response.code() == 500){
                   Log.d("Error", "Something went wrong with the server");
                   Toast toast = Toast.makeText(getApplicationContext(), "Server Error! Please try again.", Toast.LENGTH_LONG);
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           toast.show();
                       }
                   });
               }
}

           @Override
           public void onFailure(Call<List<Item>> call, Throwable t) {
               String msg = "Error in retrofit:" + t.toString();
               Log.d(TAG,msg);
               Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);

           }
       });
    }*/
      /*Item i = new Item("Candycanes", "descr", "20", "ahfshslf");
        Item i2 = new Item("Coal", "uou", "27", "hogpoh");
        items.add(i);
        items.add(i2);*/
}