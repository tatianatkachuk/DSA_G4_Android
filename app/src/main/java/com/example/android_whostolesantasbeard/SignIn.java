package com.example.android_whostolesantasbeard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
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
    // From activity
    TextView usernameVal;
    TextView passwordVal;
    Button signInButton;
    TextView swapToRegisterButton;

    // API client
    IWSSBService service;

    // Shared preferences
    SharedPreferences shPrefs;
    public static final String PREFERENCES = "prefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        service = APIClient.getClient().create(IWSSBService.class);

        usernameVal = (EditText) findViewById(R.id.usernameVal);
        passwordVal = (EditText) findViewById(R.id.passwordVal);
        signInButton = (Button) findViewById(R.id.signinButton);
        swapToRegisterButton = (TextView) findViewById(R.id.swapToRegisterButton);

        // Shared preferences. It can only be accessed by the calling application
        shPrefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        // Obtains saved prefs from file and set them to the values
        String usr = shPrefs.getString("username", "");
        usernameVal.setText(usr);
        String psw = shPrefs.getString("password","");
        passwordVal.setText(psw);


        // Swap to register or main
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
                    String username = user.getUsername();
                    String pass = user.getPassword();
                    String id = user.getId();
                    Log.d("User",username+" "+pass+" "+id);

                    // Save to shared preferences
                    saveIntoShPrefs(username, pass);

                    // Open main
                    openApp(id);
                    return;
                }
                if(response.code()==404){
                    Log.d("Error","User not found");
                    Toast toast = Toast.makeText(getApplicationContext(),"This user doesn't exist! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {toast.show();}
                    });
                    return;
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

    public void saveIntoShPrefs(String username, String password){
        SharedPreferences shPrefs;
        shPrefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shPrefs.edit();

        // Storing strings
        editor.putString("username", username);
        editor.putString("password", password);

        // Commit changes
        editor.commit();

    }

}