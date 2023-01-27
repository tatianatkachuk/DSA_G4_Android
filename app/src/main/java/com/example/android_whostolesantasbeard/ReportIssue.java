package com.example.android_whostolesantasbeard;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.content.Intent;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportIssue extends AppCompatActivity {
    // From activity
    TextView dateVal;
    TextView messageVal;
    Button submitButton;

    ImageView goBack;

    // API client
    IWSSBService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        String username = getIntent().getExtras().getString("username");

        service = APIClient.getClient().create(IWSSBService.class);

        dateVal = (TextView) findViewById(R.id.dateVal);
        dateVal.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        messageVal = (EditText) findViewById(R.id.messageVal);

        submitButton = (Button) findViewById(R.id.submitButton);

        goBack = (ImageView) findViewById(R.id.goBack);


        // Swap to register or main
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReport();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain(username);
            }
        });

    }
    public void submitReport(){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String informer = getIntent().getExtras().getString("username");
        String message = messageVal.getText().toString();

        Issue i = new Issue(date, informer, message);

        Call<Issue> call = service.reportIssue(i);

        call.enqueue(new Callback<Issue>() {
            @Override
            public void onResponse(Call<Issue> call, Response<Issue> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==201) {
                    Issue issueInfo = response.body();
                    String date = issueInfo.getDate();
                    String informer = issueInfo.getInformer();
                    String message = issueInfo.getMessage();

                    Log.d("Date",date+" informer "+informer+" message"+message);

                    Toast toast = Toast.makeText(getApplicationContext(),"Successful!", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });

                    Intent intent = new Intent(getApplicationContext(), Main.class);
                    intent.putExtra("username", getIntent().getExtras().getString("username"));
                    startActivity(intent);
                    return;
                }

                if(response.code()==500){
                    Log.d("Error","Invalid information");
                    Toast toast = Toast.makeText(getApplicationContext(),"Invalid information! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });
                    return;
                }

                else{
                    Log.d("Error","Operation failed");
                    Toast toast = Toast.makeText(getApplicationContext(),"Operation failed! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Issue> call, Throwable t) {
                call.cancel();
                Log.d("Error","Failure");
            }

        });

    }

    public void backToMain(String username) {
        Intent intent = new Intent(this, Main.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

}