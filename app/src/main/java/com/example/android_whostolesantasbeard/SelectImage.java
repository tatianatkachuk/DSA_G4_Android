package com.example.android_whostolesantasbeard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_whostolesantasbeard.entities.UserCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectImage  extends AppCompatActivity {
    IWSSBService service;

    // From activity
    ImageView imageUL; //UP LEFT
    ImageView imageUR; //UP RIGHT
    ImageView imageDL; //DOWN LEFT
    ImageView imageDR; //DOWN RIGHT

    ImageView goBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);

        service = APIClient.getClient().create(IWSSBService.class);

        String username = getIntent().getExtras().getString("username");

        imageUL = (ImageView) findViewById(R.id.avatar1);
        imageUR = (ImageView) findViewById(R.id.avatar2);
        imageDL = (ImageView) findViewById(R.id.avatar3);
        imageDR = (ImageView) findViewById(R.id.avatar4);
        goBack = (ImageView) findViewById(R.id.goBack);


        // Swap to register or main
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain(username);
            }
        });

        // Change photo
        imageUL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer selectedImage = 1;
                changePhoto(username, selectedImage);
            }
        });
        imageUR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer selectedImage = 2;
                changePhoto(username, selectedImage);
            }
        });
        imageDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer selectedImage = 3;
                changePhoto(username, selectedImage);
            }
        });
        imageDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer selectedImage = 4;
                changePhoto(username, selectedImage);
            }
        });


    }

    public void backToMain(String username) {
        Intent intent = new Intent(this, Main.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void changePhoto(String username, Integer selectedImage) {
        Call<User> call = service.getUserbyUserName(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG",response.code()+" CALL");
                if(response.code()==201){
                    User user = response.body();

                    String id = user.getId();

                    Call<User> call2 = service.updateProfileImage(id, selectedImage);
                    call2.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call2, Response<User> response) {
                            if(response.code()==201){
                                User user = response.body();

                                Integer newImageID = user.getImageID();
                                Log.d("NEW IMAGE ID",newImageID+"");

                                return;
                            }
                            else{
                                Log.d("ELSE",response.code()+"");
                            }

                        }
                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            call.cancel();
                            Log.d("Error","Failure");
                        }
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

        backToMain(username);

    }




}
