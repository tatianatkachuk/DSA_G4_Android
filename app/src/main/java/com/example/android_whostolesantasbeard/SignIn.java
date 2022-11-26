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
    Button signinButton;
    IWSSBService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        service = APIClient.getClient().create(IWSSBService.class);

        usernameVal = (EditText) findViewById(R.id.usernameVal);
        passwordVal = (EditText) findViewById(R.id.usernameVal);
        signinButton = (Button) findViewById(R.id.signinButton);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginToApp();
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
                if(response.code()==201) {
                    User user = response.body();
                    String pass = user.getPassword();
                    String username = user.getUsername();
                    String id = user.getId();
                    Log.d("User",username+" "+passwordVal+" "+id);

                    //openApp(id);
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

    /**
    public void openApp(String id) {
        Intent intent = new Intent(this, MainApp.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }**/

    public void openRegister(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}