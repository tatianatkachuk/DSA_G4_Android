package com.example.android_whostolesantasbeard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_whostolesantasbeard.entities.Query;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueryActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextDate, editTextTitle, editTextMessage, editTextSender;
    private Button btnSend;

    IWSSBService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        service = APIClient.getClient().create(IWSSBService.class);

        editTextDate=(EditText) findViewById(R.id.editTextDate);
        editTextTitle=(EditText) findViewById(R.id.editTextTitle);
        editTextMessage=(EditText) findViewById(R.id.editTextMessage);
        editTextSender=(EditText) findViewById(R.id.editTextSender);

        btnSend=(Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String date = editTextDate.getText().toString();
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();
        String sender = editTextSender.getText().toString();

        Query query = new Query(date,title, message, sender);
        Call<Query> call = service.askQuery(query);
        call.enqueue(new Callback<Query>() {
            @Override
            public void onResponse(Call<Query> call, Response<Query> response) {
                Log.d("Query",response.code()+"");
                if(response.code() == 200){
                    Query question = response.body();
                    Toast toast = Toast.makeText(getApplicationContext(),"Sent", Toast.LENGTH_SHORT);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {toast.show();}
                    });
                    Intent intent = new Intent(getApplicationContext(), Main.class);
                    startActivity(intent);
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Error on the question", Toast.LENGTH_SHORT);
                    Log.e("Error", "Error on the question");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {toast.show();}
                    });
                    Intent intent = new Intent(getApplicationContext(), QueryActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Query> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),"No connection", Toast.LENGTH_SHORT);
                Log.e("Error","Failure");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {toast.show();}
                });
                Intent intent = new Intent(getApplicationContext(), QueryActivity.class);
                startActivity(intent);
            }
        });
    }
}
