package com.example.android_whostolesantasbeard;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_whostolesantasbeard.entities.Item;
import com.example.android_whostolesantasbeard.entities.Question;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class FAQ extends AppCompatActivity {
    IWSSBService service;
    private final String TAG = FAQ.class.getSimpleName();
    private RecyclerView recyclerView;
    private FAQRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Question> questionList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        recyclerView = (RecyclerView) findViewById(R.id.m3_recyclerView);


        // use a linear layout manager
        layoutManager = new LinearLayoutManager(FAQ.this);
        recyclerView.setLayoutManager(layoutManager);

        // Set the adapter
        adapter = new FAQRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        doApiCall();
    }
    private void doApiCall()
    {
        service = APIClient.getClient().create(IWSSBService.class);
        Call<List<Question>> call = service.questionsList();
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                //set the results to the adapter
                if (response.code() == 200) {
                    List<Question> listQuestions = response.body();
                    adapter.getItemCount();
                        adapter.setData(listQuestions);
                        Log.d("Success", "Store fetched correctly!");
                        Toast toast = Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toast.show();
                            }
                        });
                        for (Question q: response.body()){
                            Log.d("API","The question id is : " + q.getId() + adapter.getItemCount());
                        }

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
            public void onFailure(Call<List<Question>> call, Throwable t) {
                String msg = "Error in retrofit:" + t.toString();
                Log.d(TAG,msg);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);

            }
        });


    }





}



