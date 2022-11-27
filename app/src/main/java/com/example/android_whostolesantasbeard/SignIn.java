package com.example.android_whostolesantasbeard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.content.Intent;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

    TextView usernameVal;
    TextView passwordVal;
    Button signInButton;
    TextView swapToRegisterButton;
    IWSSBService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        service = APIClient.getClient().create(IWSSBService.class);

        usernameVal = (EditText) findViewById(R.id.usernameVal);
        passwordVal = (EditText) findViewById(R.id.passwordVal);
        signInButton = (Button) findViewById(R.id.signinButton);
        swapToRegisterButton = (TextView) findViewById(R.id.swapToRegisterButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginToApp();
            }
        });
        swapToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterView(view);
            }
        });


    }
    public void loginToApp(){
        String username = usernameVal.getText().toString();
        String pass = passwordVal.getText().toString();
        User signInCreds = new User("", username, pass);


        Call<User> call = service.loginUser(signInCreds);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    User user = response.body();
                    String pass = user.getPassword();
                    String username = user.getUsername();
                    String id = user.getId();
                    Log.d("User",username+" "+pass+" "+id);

                    openApp(id);
                }
                if(response.code()==404){
                    Log.d("Error","User not found");
                    Toast toast = Toast.makeText(getApplicationContext(),"This user doesn't exist! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });
                }
                if(response.code()==405){
                    Log.d("Error","Wrong password");
                    Toast toast = Toast.makeText(getApplicationContext(),"The password is wrong! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });
                }
                else{
                    Log.d("Error","Login failed");
                    Toast toast = Toast.makeText(getApplicationContext(),"Login failed! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
                Log.d("Error","Failure");
            }

        });
    }

    public void openApp(String id) {
        Intent intent = new Intent(this, Main.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void openRegisterView(View view) {
        Intent intent = new Intent(SignIn.this, Register.class);
        this.startActivity(intent);
    }

}