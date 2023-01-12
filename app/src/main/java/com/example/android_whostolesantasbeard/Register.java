package com.example.android_whostolesantasbeard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_whostolesantasbeard.entities.SignInCredentials;
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
                Log.d("SANTA",response.code()+"");
                if(response.code() ==201 ){
                    Log.d("SANTA",response.code()+"");
                    Toast toast = Toast.makeText(getApplicationContext(),"Success!", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });
                    openSignIn();
                    //Todo bien pa
                    return;
                }
                if(response.code() ==405 ){
                    //Username already in use
                    Log.d("Error","Username already in use");
                    Toast toast = Toast.makeText(getApplicationContext(),"Username already in use! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });
                    return;
                }
                if(response.code() ==406 ){
                    //email already in use
                    Log.d("Error","Email already in use");
                    Toast toast = Toast.makeText(getApplicationContext(),"Email already in use! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });
                    return;
                }
                if(response.code() ==500 ){
                    //Validation Error
                    Log.d("Error","Server internal error");
                    Toast toast = Toast.makeText(getApplicationContext(),"Server Error! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });

                }

                else{
                    Log.d("Error","Sign Up failed");
                    Toast toast = Toast.makeText(getApplicationContext(),"Sign Up failed! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });

                }

            }
            @Override
            public void onFailure(Call<UserCredentials> call, Throwable t) {
                call.cancel();
                Log.d("Error","Failure");
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
            public void onClick(View view) {openLoginView(view);}
        });
    }

    public void openLoginView(View view) {
        Intent intent = new Intent(Register.this, SignIn.class);
        startActivity(intent);
    }

    public void openSignIn() {
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

}