package com.example.android_whostolesantasbeard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_whostolesantasbeard.entities.UserCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    TextView usernameTextField;
    TextView emailTextField;
    TextView passwordTextField;
    TextView goToSignInButton;
    Button registerButton;
    IWSSBService service;

    public void attemptRegistration(){
        String username = usernameTextField.getText().toString();
        String email = emailTextField.getText().toString();
        String password = passwordTextField.getText().toString();
        UserCredentials userCredentials = new UserCredentials (username,email,password);
        Call<UserCredentials> call = service.registerAccount(userCredentials);
        call.enqueue(new Callback<UserCredentials>() {
            @Override
            public void onResponse(Call<UserCredentials> call, Response<UserCredentials> response) {
                if(response.code() ==201 ){
                    //Todo bien pa
                    return;
                }
                if(response.code() ==405 ){
                    //Username already in use
                    return;
                }
                if(response.code() ==406 ){
                    //email already in use
                    return;
                }
                if(response.code() ==500 ){
                    //Validation Error
                    return;
                }

            }
            @Override
            public void onFailure(Call<UserCredentials> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),"Something failed on the register...",Toast.LENGTH_LONG);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toast.show();
                    }
                });
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        service = APIClient.getClient().create(IWSSBService.class);
        goToSignInButton = (TextView) findViewById(R.id.backToSignIn);
        usernameTextField = (TextView) findViewById(R.id.usernameTextField);
        emailTextField = (TextView) findViewById(R.id.emailTextField);
        passwordTextField = (TextView) findViewById(R.id.passwordTextField);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });
        goToSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, SignIn.class);
                startActivity(intent);
            }
        });
    }
}