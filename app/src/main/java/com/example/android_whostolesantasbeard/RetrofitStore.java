package com.example.android_whostolesantasbeard;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_whostolesantasbeard.entities.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitStore extends AppCompatActivity {

    ArrayList<Item> items;
    IWSSBService service= APIClient.getClient().create(IWSSBService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofitstore);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(view -> {
            Call<List<Item>> call = service.itemsList(items);

            call.enqueue(new Callback<List<Item>>() {
                @Override
                public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                    final TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText(response.body().toString());
                }
                @Override
                public void onFailure(Call<List<Item>> call, Throwable t) {
                    final TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText("Something went wrong: " + t.getMessage());
                }
            });
        });
    }
}


