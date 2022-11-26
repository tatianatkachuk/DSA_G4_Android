package com.example.android_whostolesantasbeard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_whostolesantasbeard.entities.UserCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView usernameTextField;
    TextView emailTextField;
    TextView passwordTextField;
    Button registerButton;
    IWSSBService service;

    public void attemptRegistration(){
        String username = usernameTextField.getText().toString();
        String email = emailTextField.getText().toString();
        String password = passwordTextField.getText().toString();
        UserCredentials userCredentials = new UserCredentials (username,email,password);
        Call call = service.registerAccount(userCredentials);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                usernameTextField.setText("AAAAH IT WOKED");
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                usernameTextField.setText("AAAAH IT FAILED");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofitSetUp();
        usernameTextField = (TextView) findViewById(R.id.usernameTextField);
        emailTextField = (TextView) findViewById(R.id.emailTextField);
        passwordTextField = (TextView) findViewById(R.id.passwordVal);
        registerButton = (Button) findViewById(R.id.signinButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });
    }
    //ToDO : Averiguar cómo demonios atacar la api en local o bien subirlo cuanto antes al servidor real.
    private void retrofitSetUp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/swagger/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(IWSSBService.class);
    }
}